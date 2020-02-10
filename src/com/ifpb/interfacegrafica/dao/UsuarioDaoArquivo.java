package com.ifpb.interfacegrafica.dao;

import com.ifpb.interfacegrafica.modelo.Usuario;

import java.io.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class UsuarioDaoArquivo {

//    public static void escritor(String path) throws IOException {
//        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
//        String linha = "";
//        System.out.println("Escreva algo: ");
//        Scanner in;
//        linha = in.nextLine();
//        buffWrite.append(linha + "\n");
//        buffWrite.close();
//    }
//
    private File arquivo;
//Cria o arquivo
    public UsuarioDaoArquivo() throws IOException {
        arquivo = new File("usuarios");

        if(!arquivo.exists()) arquivo.createNewFile();
    }

    public Set<Usuario> getUsuarios() throws IOException,
            ClassNotFoundException {
        if(arquivo.length()>0){
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(arquivo)
            );
            return (HashSet<Usuario>) in.readObject();
        }else return new HashSet<>();
    }

    public Usuario buscarPorEmail(String email) throws IOException,
            ClassNotFoundException {
        return getUsuarios().stream().filter(
                u -> u.getEmail().equals(email)
        ).findFirst().orElse(null);
    }

    public boolean salvar(Usuario usuario) throws IOException,
            ClassNotFoundException {
        Set<Usuario> usuarios = getUsuarios();
        return usuarios.add(usuario) && atualizarArquivo(usuarios);
    }

    private boolean atualizarArquivo(Set<Usuario> usuarios) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(arquivo)
        );
        out.writeObject(usuarios);
        return true;
    }

    //TODO Criar método de atualizar e remover
//fiz os metodos e um adcinou para buscar o usuário pelo email
    private Optional<Usuario> filtrarUsuario(Set<Usuario> usuarios, String email){
        return usuarios.stream().filter(e -> e.getEmail().equals(email)).findFirst();
    }


    public boolean atualizar(Usuario usuario) throws IOException, ClassNotFoundException {
        Set<Usuario> usuarios = getUsuarios();
        Optional<Usuario> optionalUsuario = filtrarUsuario(usuarios, usuario.getEmail());

        if (optionalUsuario.isEmpty()) {
            usuarios.remove(optionalUsuario.get());
            usuarios.add(usuario);
            atualizarArquivo(usuarios);
            return true;
        }else{
            return false;
        }

    }

    public boolean removerUsuario (Usuario usuario) throws IOException, ClassNotFoundException{
        Set<Usuario> usuarios = getUsuarios();
        if(usuarios.remove(usuario)){
            atualizarArquivo(usuarios);
            return true;
        }
        return false;
    }
}
