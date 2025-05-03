package com.cloudfly03.servlet;

import com.cloudfly03.factory.BeanFactory;
import com.cloudfly03.service.HelloService;
import com.cloudfly03.service.impl.HelloServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private final HelloService helloService = new HelloServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(this.helloService.findAll().toString());
    }
}
