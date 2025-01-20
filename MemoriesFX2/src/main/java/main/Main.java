package main;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import handlers.PictureHandler;
import persist.ApplicationException;



public class Main {
	
	//Criar uma instância centralizada do EntityManagerFactory (default para o test).
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAMemoriesTest");;

	
}
