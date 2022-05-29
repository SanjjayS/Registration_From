import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
public class RegisterationFormCA {

Connection con;
Statement st;
PreparedStatement ps;
ResultSet rt;
RegisterationFormCA(Object[][] data) throws SQLException
{
try
{
con = DriverManager.getConnection("jdbc:derby:ca;create=true");
st = con.createStatement();
// st.executeUpdate("drop table formca");
DatabaseMetaData dbm = con.getMetaData();
// check if "employee" table is there
ResultSet tables = dbm.getTables(null, "APP", "FORMCA", null);
if (tables.next()) {
 // Table exists
// System.out.println("hit exist");
}
else {
 // Table does not exist
st.executeUpdate("create table formca(id varchar(20),name varchar(20),gender varchar(10),address varchar(50),contact varchar(20))");
}
ps = con.prepareStatement("insert into formca values(?,?,?,?,?)");
}
catch(Exception e)
{
e.printStackTrace();
}
JFrame f = new JFrame();
f.setTitle("Registeration Form");
JPanel leftpanel = new JPanel();
Border b = BorderFactory.createLineBorder(Color.black);
JPanel headingleftpanel = new JPanel();
JLabel lheading = new JLabel("Registeration Form",JLabel.CENTER);
headingleftpanel.add(lheading);

JPanel contentleftpanel = new JPanel();

JLabel id = new JLabel("Id");
JTextField idf = new JTextField();
idf.setPreferredSize(new Dimension(150,20));
contentleftpanel.add(id);
contentleftpanel.add(idf);

JLabel name = new JLabel("Name");
JTextField namef = new JTextField();
contentleftpanel.add(name);
contentleftpanel.add(namef);

JPanel genderp = new JPanel();
        JLabel gender = new JLabel("Gender : ");
        JRadioButton male = new JRadioButton("Male");
        male.setActionCommand("Male");
        JRadioButton female = new JRadioButton("Female");
        female.setActionCommand("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(male);bg.add(female);
        genderp.add(male);genderp.add(female);
       
        contentleftpanel.add(gender);
        contentleftpanel.add(genderp);

JLabel add = new JLabel("Address");
JTextField addf = new JTextField();
contentleftpanel.add(add);
contentleftpanel.add(addf);

JLabel contact = new JLabel("Contact");
JTextField contactf = new JTextField();
contentleftpanel.add(contact);
contentleftpanel.add(contactf);

JButton exit = new JButton("Exit");
contentleftpanel.add(exit);
JButton register = new JButton("Register");
contentleftpanel.add(register);

JButton delete = new JButton("Delete");
delete.setEnabled(false);
contentleftpanel.add(delete);
JButton update = new JButton("Update");
update.setEnabled(false);
contentleftpanel.add(update);
JButton reset = new JButton("Reset");
reset.setEnabled(false);
contentleftpanel.add(reset);

contentleftpanel.setLayout(new GridLayout(8,2,30,30));

JPanel rightpanel = new JPanel();
String[] columnNames= {"S.No","ID","Name", "Gender","Address", "Contact"};
JPanel tablep = new JPanel();
JTable table = new JTable(data,columnNames);
JScrollPane pane = new JScrollPane(table);
table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
tablep.add(pane);

exit.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e)
{
f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
}
});

register.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e)
{
try {
ps.setString(1,idf.getText());
ps.setString(2,namef.getText());
ps.setString(3, bg.getSelection().getActionCommand());
ps.setString(4,addf.getText());
ps.setString(5,contactf.getText());
JOptionPane.showMessageDialog(null,"Succesfully Registered.");
idf.setText("");
namef.setText("");
addf.setText("");
contactf.setText("");
ps.executeUpdate();
bg.clearSelection();
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}

}
});
        JPanel p = new JPanel();
        JButton refresh = new JButton("Refresh Table");
        refresh.setPreferredSize(new Dimension(150,20));
        p.add(refresh);
       
        refresh.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e)
        {
        int i = 1;
        Object[][] data1 = new Object[1000][10];
        try {
rt = st.executeQuery("Select * from formca");
while(rt.next())
{
data1[i-1][0]= i;
data1[i-1][1] = rt.getString(1);
data1[i-1][2] = rt.getString(2);
data1[i-1][3] = rt.getString(3);
data1[i-1][4] = rt.getString(4);
data1[i-1][5] = rt.getString(5);
++i;
}
f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
new RegisterationFormCA(data1);
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
        }
        });
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablep.add(p);
        tablep.setLayout(new BoxLayout(tablep,BoxLayout.Y_AXIS));
        rightpanel.add(tablep,BorderLayout.EAST);
leftpanel.add(headingleftpanel);
headingleftpanel.setPreferredSize(new Dimension(500,40));
leftpanel.add(contentleftpanel);
leftpanel.setLayout(new BoxLayout(leftpanel,BoxLayout.Y_AXIS));
f.add(leftpanel,BorderLayout.WEST);
f.add(rightpanel);
f.setLayout(new FlowLayout(FlowLayout.CENTER,100,10));
f.setSize(1280,1024);
f.setVisible(true);
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}
public static void main(String args[]) throws SQLException
{
// Connection c = DriverManager.getConnection("jdbc:derby:ca;create=false");
Object[][] data = {};
new RegisterationFormCA(data);
}
}