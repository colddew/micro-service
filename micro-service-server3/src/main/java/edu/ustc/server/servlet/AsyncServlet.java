//package edu.ustc.server.servlet;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.AsyncContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Created by colddew on 2019/5/7.
// */
//@WebServlet(urlPatterns = "/servlet/async", asyncSupported = true)
//public class AsyncServlet extends HttpServlet {
//
//    private static final Logger logger = LoggerFactory.getLogger(AsyncServlet.class);
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        AsyncContext asyncContext = req.startAsync();
//        asyncContext.start(() -> {
//            try {
//                resp.getWriter().println("hello world");
//                // trigger complete
//                asyncContext.complete();
//            } catch (IOException e) {
//                logger.error(e.getMessage());
//            }
//        });
//    }
//}
