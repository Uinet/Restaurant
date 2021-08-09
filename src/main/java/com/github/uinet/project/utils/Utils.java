package com.github.uinet.project.utils;

import com.github.uinet.project.domain.Orders;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    public static Orders getOrderFromSession(HttpServletRequest httpServletRequest){

        Orders cart = (Orders) httpServletRequest.getSession().getAttribute("order");

        if(cart==null){
            cart =  new Orders();
            httpServletRequest.getSession().setAttribute("order", cart);
        }

        return cart;
    }

    public static void removeOrderFromSession(HttpServletRequest httpServletRequest){

        httpServletRequest.getSession().removeAttribute("order");
    }
}
