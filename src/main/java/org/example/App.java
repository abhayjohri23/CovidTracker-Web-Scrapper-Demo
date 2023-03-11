package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;

public class App
{
    public static String getData(String countryName) throws Exception
    {
        String url="https://www.worldometers.info/coronavirus/country/".concat(countryName).concat("/");
        Document doc= Jsoup.connect(url).get();         //It is jsoup document and it is different DOM document interface
        Elements elements=doc.select("#maincounter-wrap");      //It is a selector for jsoup objects. It has a CSS query parameter which if matched
                                                                        //with any syntax inside the HTML of a document/element returns that element.
//      System.out.println(elements.html());        //We can print object directly also. Elements is a collection of Element objects. html() returns String

        StringBuilder output=new StringBuilder();
        output.append("<html>").append("<br>").append(countryName.toUpperCase()).append("<br>");

        for(int i=0;i<3;++i)
        {
            Element e=elements.get(i);
            String text=e.select("h1").text();
            String count=e.select(".maincounter-number>span").text();

            output.append(text).append(" "+count).append("<br>");
        }

        output.append("</html>");

        return output.toString();

    }

    public static void main( String[] args ) throws Exception {

        JFrame frame=new JFrame("Covid cases - Country wise");
        frame.setSize(500,500);

        Font font=new Font("Poppins",Font.BOLD,30);

        //Text field creation
        JTextField field=new JTextField();
        field.setFont(font);
        field.setHorizontalAlignment(SwingConstants.CENTER);

        //Label creation
        JLabel label=new JLabel();
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        //Go Button creation
        JButton go_button=new JButton("SEARCH");
        go_button.setFont(font);
        go_button.setHorizontalAlignment(SwingConstants.CENTER);

        //Setting the layout and positions of the components
        frame.setLayout(new BorderLayout());
        frame.add(field,BorderLayout.NORTH);
        frame.add(label,BorderLayout.CENTER);
        frame.add(go_button, BorderLayout.SOUTH);


        //Adding Listener to the button
        go_button.addActionListener((event)->{
            String query=field.getText().trim().toLowerCase();
            try {
                label.setText(getData(query));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        frame.setVisible(true);
    }
}
