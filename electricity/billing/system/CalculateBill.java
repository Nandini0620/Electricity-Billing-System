package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class CalculateBill extends JFrame implements ActionListener
{
    JTextField tfunits;
    JButton next,cancel;
    JLabel lblmetername,lbladdress;
    Choice meternumber ,cmonth;
    CalculateBill()
    {
        setSize(700,500);
        setLocation(400,150);
        
        JPanel p=new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173,216,230));
        add(p);
        
        JLabel heading=new JLabel("Calculate Electricity Bill");
        heading.setBounds(120,10,500,40);
        //heading.setForeground(Color.GRAY);
        heading.setFont(new Font("Tahoma",Font.BOLD,24));
        p.add(heading);
        
        
        JLabel lblmeter=new JLabel("Meter Number");
        lblmeter.setBounds(100,80,100,20);
        p.add(lblmeter);
      
        meternumber=new Choice();
        try
        {
            Conn c=new Conn();
            ResultSet rs=c.s.executeQuery("select * from customer");
            while(rs.next())
            {
                meternumber.add(rs.getString("meter_no"));
            } 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        meternumber.setBounds(240,80,200,20);
        p.add(meternumber);
        
        JLabel lblname=new JLabel("Name");
        lblname.setBounds(100,120,100,20);
        p.add(lblname);
        
        lblmetername=new JLabel("");
        lblmetername.setBounds(240,120,200,20);
        p.add(lblmetername);
        
        JLabel lblcity=new JLabel("Address");
        lblcity.setBounds(100,160,100,20);
        p.add(lblcity);
        
        lbladdress=new JLabel();
        lbladdress.setBounds(240,160,200,20);
        try
        {
            Conn c=new Conn();
            ResultSet rs=c.s.executeQuery("select * from customer where meter_no='"+meternumber.getSelectedItem()+"'");
            while(rs.next())
            {
                lblmetername.setText(rs.getString("name"));
                lbladdress.setText(rs.getString("address"));

            } 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        p.add(lbladdress);
        
        
        meternumber.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent ie)
            {
                try
        {
            Conn c=new Conn();
            ResultSet rs=c.s.executeQuery("select * from customer where meter_no='"+meternumber.getSelectedItem()+"'");
            while(rs.next())
            {
                lblmetername.setText(rs.getString("name"));
                lbladdress.setText(rs.getString("address"));
            } 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
            }
        });
        
        JLabel lblunit=new JLabel("Unit Consumed");
        lblunit.setBounds(100,200,100,20);
        p.add(lblunit);
        
        tfunits=new JTextField();
        tfunits.setBounds(240,200,200,20);
        p.add(tfunits);
        
        JLabel lblmonth=new JLabel("Month");
        lblmonth.setBounds(100,240,100,20);
        p.add(lblmonth);
        
        cmonth=new Choice();
        cmonth.setBounds(240,240,200,20);
        cmonth.add("January");
        cmonth.add("Febuary");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        p.add(cmonth);
        
        next=new JButton("Submit");
        next.setBounds(120,350,100,25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);
        
        cancel=new JButton("Cancel");
        cancel.setBounds(250,350,100,25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        p.add(cancel);
        
        setLayout(new BorderLayout());
        add(p,"Center");
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
        Image i2=i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel image=new JLabel(i3);
        add(image,"West");
        
        getContentPane().setBackground(Color.WHITE);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()== next)
        {
            String mnumber=meternumber.getSelectedItem();
           // String name=lblmetername.getText();
           // String address=lbladdress.getText();
            String units=tfunits.getText();
            String month=cmonth.getSelectedItem();
            
            int totalbill=0;
          
             String query1="select * from tax";
             try
             {
                 Conn c=new Conn();
                 ResultSet rs=c.s.executeQuery(query1);
                 while(rs.next())
                 {
                     totalbill +=(Integer.parseInt(units))* (Integer.parseInt(rs.getString("cost_per_unit")));
                     totalbill+=Integer.parseInt(rs.getString("meter_rent"));
                     totalbill+=Integer.parseInt(rs.getString("service_charge"));
                     totalbill+=Integer.parseInt(rs.getString("service_tax"));
                     totalbill+=Integer.parseInt(rs.getString("swacch_bharat_cess"));
                     totalbill+=Integer.parseInt(rs.getString("fixed_tax"));
                 }
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
             
             String query2="insert into bill values('"+mnumber+"','"+month+"','"+units+"','"+totalbill+"','Not Paid')";
             try
             {
                 Conn c=new Conn();
                 c.s.executeUpdate(query2);
                 JOptionPane.showMessageDialog(null, "Customer Bill Updated Successfully");
                 setVisible(false);
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
        }
        else if(ae.getSource()== cancel)
        {
            setVisible(false);
        }
    }
    
    public static void main(String ar[])
    {
        new CalculateBill();
    }
    
}
