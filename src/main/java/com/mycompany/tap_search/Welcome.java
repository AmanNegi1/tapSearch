/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tap_search;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ANON
 */
@WebServlet(name = "Welcome", urlPatterns = {"/Welcome"})
public class Welcome extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    static HashMap<String,ArrayList<String>> Mapping = new HashMap<String,ArrayList<String>>();
    
    static HashMap<String,String > doc_Mapping = new HashMap<>(); 
    
    protected static void mappingWordToDocuments(String documents[]){
        int c=1;
        
        for(String doc : documents){
             /*
             
             Step 1: This pick paragraph.
             Step 2: split this paragraph into words.
             Step 3: Maped this Paragraph to these words 
             Here I have Used HashMap for Mapping the word.
             There are to 2 Map I have used here
             1: Mapping --> this maps words to their respective document.
             2: doc_Mapping --> here unique_id (paragraph1,paragraph2 etc.) is mapped to paragraph. 
        
             */
            String temp [] = doc.split("\\s+");
            doc_Mapping.put("paragraph"+c, doc);
            for(int i=0;i<temp.length;i++){
                String lower= temp[i].toLowerCase();
                
                if(Mapping.containsKey(lower)){
                    ArrayList<String> tempAl=Mapping.get(lower);
                    if(!tempAl.contains("paragraph"+c)){
                        tempAl.add("paragraph"+c);
                        Mapping.put(lower,tempAl);
                    }
                    
                }else{
                    ArrayList<String> newAL=new ArrayList<>();
                    newAL.add("paragraph"+c);   
                    Mapping.put(lower,newAL);
                }
            }
            c++;
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Welcome</title>");            
            out.println("</head>");
            out.println("<body>");
            /*
            
             Get the  text user enter in the form then Split by newline into paragraph..
            
            */
            String documents[] = request.getParameter("Textarea").split("[\\r\\n]+");
            /*
            
            wordToBeSearch is the text which user want to search the index.
            
            */
            
            String wordToBeSearch=request.getParameter("Text").toLowerCase();
            
            /*
            
             By calling below function I have Mapped the word repective to their paragraph , if a word belong to more than one paragraph then
             I have also mapped those to paragraph
            
            */
            mappingWordToDocuments(documents);
            /*
            
             In the below lines I have simply retrived the paragraphs uniqueId where we found search word.
            
            */
            if(Mapping.containsKey(wordToBeSearch)){
            ArrayList<String> answer=Mapping.get(wordToBeSearch); 
            
            if(answer.size()!=0 && Mapping.containsKey(wordToBeSearch)){
                
            out.println("<h2> "+wordToBeSearch+" is found at paragraphs "+answer+"</h2>");
            for(int i=0;i<answer.size();i++){
                out.println("<h4>"+answer.get(i)+"  =  \""+doc_Mapping.get(answer.get(i))+" \" </h4>");
            }
            }}else{
                
                out.println("<h2>Word not found</h2>");
               
            }
            
            out.println();
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

