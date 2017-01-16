import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sachin vashistha
 */
public class Credit_Logic extends HttpServlet {
    int cred_bank_dec;
    PreparedStatement pstmt=null;
    ResultSet rs=null;
    Connection con;
@Override
public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
    HttpSession htsession=request.getSession(false);
    String email=(String)htsession.getAttribute("current_email");
    String password=(String)htsession.getAttribute("current_pass");
    System.out.println(email+"    "+password);
                try
            {
             Class.forName("org.apache.derby.jdbc.ClientDriver");
              con=DriverManager.getConnection("jdbc:derby://localhost:1527/Login_Database","sachin","33sacv");
             PreparedStatement ps =con.prepareStatement
                             ("select CREDITNU from SACHIN.REGISTERED where email=? and password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs =ps.executeQuery();
            while(rs.next()){
            cred_bank_dec=rs.getInt("creditnu");
            decrease_the_credits(cred_bank_dec,email,password);
            }
            }catch(Exception ex)
            {
                System.out.println("cannot fetch required value from database due to some error");
                ex.printStackTrace();
            }
            RequestDispatcher rd=request.getRequestDispatcher("paymentform.jsp");
            rd.forward(request, response);
}
    public void decrease_the_credits(int cred_,String email,String password)
{
    cred_bank_dec=cred_-1;
    try{
    Statement statement=con.createStatement();
    String query="UPDATE SACHIN.REGISTERED SET creditnu=? where email=? and password=?";
    pstmt=con.prepareStatement(query);
    pstmt.setInt(1,cred_bank_dec);
    pstmt.setString(2, email);
    pstmt.setString(3, password);
    pstmt.executeUpdate();
    System.out.println("credits decreased susscessfully");
    }catch(Exception ex)
    {
        System.out.println("cannot update database due to some error");
        ex.printStackTrace();
    }
}
}