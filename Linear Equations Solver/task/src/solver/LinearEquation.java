package solver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Su unico proposito es servir como un envoltorio
 * para formatear numeros complejos y mostras las ecuaciones, aunque
 * nunca uso esta ultima
 */
public class LinearEquation {
    // Lista que contiene los coeficientes de la ecuacion
    protected List<Complex> coeficients;

    //Con este constructor crearemos las ecuaciones a partir
    //de una cadena de texto
    LinearEquation(String equation) {
        coeficients = new ArrayList<>();
        equation = equation.trim();

        //Leemos todos los coeficientes y los separamos por el espacio
        String[] coef = equation.split("[\\s]");

        //Regex que representa un numero real
        String real_pattern = "[\\+-]?\\d+(\\.\\d+)?";
        //Regex que representa un numero complejo
        String imag_pattern = "(" + real_pattern + "|[\\+-])?i";

        Pattern realP = Pattern.compile(real_pattern);
        Pattern imagP = Pattern.compile(imag_pattern);

        for (String x : coef) {
            //Conseguimos los Matchers del token actual (un coeficiente)
            //uno para los numeros reales y otro para complejos
            Matcher realM = realP.matcher(x);
            Matcher imagM = imagP.matcher(x);


            /*
            * Primero vemos si hay un token que encaje con el regex de un
            * numero complejo, si es asi lo tomamos y acondicionamos de tal
            * manera que tenga el formato ai (no solo un -i o i).
             */
            String imag = imagM.find() ? imagM.group() : "0i";
            imag = imag.equals("i") || imag.equals("+i") ? "1i" : imag;
            imag = imag.equals("-i") || imag.equals("-i") ? "-1i" : imag;

            //Eliminamos el numero complejo si es que lo encontramos
            x = x.replaceAll(imag_pattern,"");
            //Como hemos cambiado 'x', actualizamos el matcher de real
            // con el nuevo valor de x
            realM = realP.matcher(x);
            //Si no exite una parte real, entonces su valor es 0
            String real = realM.find() ? realM.group() : "0";

            double r = Double.valueOf(real);
            double i = Double.valueOf(imag.substring(0,imag.length() - 1));
            coeficients.add(new Complex(r,i));
        }
    }

    LinearEquation(List<Complex> coef){
        this.coeficients = coef;
    }

    //Como las ecuaciones y las filas están intimamente ligadas
    //creo está funcion para devolver una fila
    public Row getRow(){
        return new Row(this);
    }

    //Funcion toString util para mostrar la ecuacion
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < coeficients.size(); i++) {
            sb.append(" + ");
            sb.append(coeficients.get(i)).append(" * ").append("R").append(i+1);
        }
        return sb.toString();
    }
}