
package házi;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Házi 
{
    private static Statement stmt = null;
    private static Map<String, User> users = new TreeMap<>();
    private static int STATE = 0;
    private static String profil = "";
    
    public static void main(String[] args) 
    {
        users.put("proba", new User("proba1@gmail.com", "proba"));
        users.put("Pali", new User("proba2@gmail.com", "Pali"));
        functions(0);
    }
    
    private static void functions(int fts) 
    {
        Scanner scanner = new Scanner(System.in);
        if (STATE == 0)
        {  
            if(fts == 0)
            {
                System.out.println("MENÜ:\n1 - Bejelentkezés\n2 - Regisztráció\n3 - Kilépés");
                int a = 0; 
                try
                {
                    a = scanner.nextInt();
                    
                }
                catch(Exception e)
                {
                    System.out.println("ÉRVÉNYTELEN érték");
                    functions(0);
                }
                switch(a)
                {
                        case 1: functions(1); break;
                        case 2: functions(2); break;
                        case 3: System.exit(0); break;
                        default :  functions(0);
                }
            }
            else if(fts== 1)
            {
                System.out.println("Add meg az Email címed\n Email:");
                String email = scanner.nextLine();
                if(email.split(" ").length == 2)
                {
                    
                        System.out.println("Nem lehet szóköz!");
                        
                }
                else            
                    System.out.println(login(email) ? "" : "Nem létező Email cím\n");
                    functions(0);
            }
            else if(fts == 2)
            {
                System.out.println("Add meg a regisztrálni kívánt Email címet:\nEmail:");
                register();
                functions(1);
            }
        }
        else if(STATE == 1)
        {
            if(fts == 0)
            {
                System.out.println("MENÜ:\n1 - Üzenetek\n2 - Üzenet küldése\n3 - Kijelentkezés");
                    int a = scanner.nextInt();
                    functions(a);
            }
            else if(fts == 1)
            {                
                users.get(profil).getMsg();  
               functions(0);
            }
            else if(fts == 2)
            {
                System.out.println("Add meg a címzett Email címét:\n");
                String cimzett = scanner.next();
                if(!users.containsKey(cimzett))
                {
                    System.out.println("Nem létező Email");
                    functions(2);
                }
                System.out.println("Üzenet:\n");
                String torzs = scanner.next();
                users.get(profil).To(new Message(profil, torzs, cimzett)); 
                users.get(profil).From(new Message(profil, torzs, cimzett));
                functions(0);
            }
            else if(fts == 3)
            {
                STATE = 0;
                System.out.println("Sikeresen kijelentkeztél!!\n");
                functions(0);
            }
        }
    }
    
    private static void register()
    {
        Scanner scanner = new Scanner(System.in);
        String email = scanner.next();
        System.out.println("Add meg a felhasználó nevet:\n");
        String nick = scanner.next();
        users.put(email, new User(email, nick));
    }
     
    private static boolean login(String _email)
    {
        if(users.containsKey(_email))
        {
            profil = _email;
            STATE = 1;
            return true;
        }
      return false;
    }     
}

class User{
    private String email;
    private String name;
    private Set<Message> receaved = new HashSet<>();
    private Set<Message> sent = new HashSet<>();
    
    public User( String email, String name) 
    {
        this.name = name;
        this.email = email;
    }   
    public void From(Message m)
    {
        receaved.add(m);
    } 
    public void To(Message m2)
    {
        sent.add(m2);

    }
    public void getMsg()
    {
         System.out.println("--------------");
        System.out.println("BEJÖVŐ ÜZENETEK");
        for(Message elem: receaved)
        {
            System.out.println(elem);
        }
        System.out.println("\nELKÜLDÖTT ÜZENETEK\n");
        for(Message elem: sent)
        {
            System.out.println(elem);
        }
        System.out.println("--------------");
    }
    
    @Override
    public String toString()
    {
        return "Email: " + email + " | Név: "+ name;
    }

    
}

class Message
{
    private String felado;
    private String torzs;
    private String cimzett;

    public Message(String felado, String torzs, String cimzett) 
    {
        this.felado = felado;
        this.torzs = torzs;
        this.cimzett = cimzett;
    }
    public String getFelado() 
    {
        return felado;
    }
    public String getTorzs() 
    {
        return torzs;
    }
    public String getCimzett() 
    {
        return cimzett;
    }
    
    @Override
    public String toString()
    {
        return "Felado: " + felado + "\nCímzett: " + cimzett + "\n "+torzs;
    }
}