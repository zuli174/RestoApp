
package Aplikasi;
public class Session {
    private static  String idpengguna;
    private static  String namapengguna;
    private static  String level;
    private static  String username;
    private static  String password;
    
    public static  String getIdpengguna() {
        return idpengguna;
    }
    public static void setIdpengguna(String idpengguna) {
        Session.idpengguna = idpengguna;
    }
    
    public static  String getNamaPengguna() {
        return namapengguna;
    }
    public static void setNamaPengguna(String namapengguna) {
        Session.namapengguna = namapengguna;
    }
    
    public static  String getLevel() {
        return level;
    }
    public static void setLevel(String level) {
        Session.level = level;
    }
    
    public static  String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        Session.username = username;
    }
    
    public static  String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        Session.password = password;
    }
    
    public static void hapus() {
        idpengguna="";
        namapengguna="";
        username="";
        password="";
        level="";
    }
}
