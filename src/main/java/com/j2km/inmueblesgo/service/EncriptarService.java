/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.ejb.Stateless;

@Stateless
public class EncriptarService implements Serializable {

    public String encriptar(String cadena) {         
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = cadena.getBytes();
            byte[] hash = md.digest(passwordBytes);         
            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

}
