package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class MeterInfo extends JFrame implements ActionListener
{
    JTextField tfname,tfaddress,tfcity,tfstate,tfemail,tfphone;
    JButton next,cancel;
    JLabel lblmeternumber;
    Choice meterlocation,metertype,phasecode,billtype;
    String meterno;
    MeterInfo(String meterno)
    {
        this.meterno=meterno;
        setSize(700,500);
        setLocation(400,200);
        
        JPanel p=new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173,216,230));
        add(p);
        
        JLabel heading=new JLabel("Meter Information");
        heading.setBounds(180,10,500,20);
        //heading.setForeground(Color.GRAY);
        heading.setFont(new Font("Tahoma",Font.BOLD,24));
        p.add(heading);
        
        
        JLabel lblmeter=new JLabel("Meter Number");
        lblmeter.setBounds(100,80,100,20);
        p.add(lblmeter);
        
        lblmeternumber=new JLabel(meterno);
        lblmeternumber.setBounds(240,80,100,20);
        p.add(lblmeternumber);
        
        JLabel lblmeterloc=new JLabel("Meter Location");
        lblmeterloc.setBounds(100,120,100,20);
        p.add(lblmeterloc);
        
        meterlocation=new Choice();
        meterlocation.setBounds(240,120,200,20);
        meterlocation.add("Outside");
        meterlocation.add("Inside");
        p.add(meterlocation);
        
        JLabel lbltype=new JLabel("Meter Type");
        lbltype.setBounds(100,160,100,20);
        p.add(lbltype);
        
        metertype=new Choice();
        metertype.setBounds(240,160,200,20);
        metertype.add("Electric Meter");
        metertype.add("Solar Meter");
        metertype.add("Smart Meter");
        p.add(metertype);
        
        JLabel lblcity=new JLabel("Phase Code");
        lblcity.setBounds(100,200,100,20);
        p.add(lblcity);
        
        phasecode=new Choice();
        phasecode.setBounds(240,200,200,20);
        phasecode.add("011");
        phasecode.add("022");
        phasecode.add("033");
        phasecode.add("044");
        phasecode.add("055");
        phasecode.add("066");
        phasecode.add("077");
        phasecode.add("088");
        phasecode.add("099");
        p.add(phasecode);
        
        JLabel lblstate=new JLabel("Bill Tye");
        lblstate.setBounds(100,240,100,20);
        p.add(lblstate);
        
        billtype=new Choice();
        billtype.setBounds(240,240,200,20);
        billtype.add("Normal");
        billtype.add("Industrial");
        p.add(billtype);
        
        JLabel lblemail=new JLabel("Days");
        lblemail.setBounds(100,280,100,20);
        p.add(lblemail);
        
        JLabel lbldays=new JLabel("30 Days");
        lbldays.setBounds(240,280,200,20);
        p.add(lbldays);
        
        JLabel lblnote=new JLabel("Note");
        lblnote.setBounds(100,320,200,20);
        p.add(lblnote);
        
        JLabel lblnotes=new JLabel("By default Bill is calculated for 30 days only");
        lblnotes.setBounds(240,320,500,20);
        p.add(lblnotes);
        
        next=new JButton("Submit");
        next.setBounds(220,390,100,25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);
        
        
        setLayout(new BorderLayout());
        add(p,"Center");
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
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
            String meter=lblmeternumber.getText();
            String location=meterlocation.getSelectedItem();
            String mtype=metertype.getSelectedItem();
            String code=phasecode.getSelectedItem();
            String btype=billtype.getSelectedItem();
            String days="30";
            
            
             String query1="insert into meter_info values('"+meter+"','"+location+"','"+mtype+"','"+code+"','"+btype+"','"+days+"')";
             
             try
             {
                 Conn c=new Conn();
                 c.s.executeUpdate(query1);
                 
                 
                 JOptionPane.showMessageDialog(null, "Meter Information Added Successfully");
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
        new MeterInfo("");
    }
    
}
