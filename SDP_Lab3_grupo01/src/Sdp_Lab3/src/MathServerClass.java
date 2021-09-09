import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;

public class MathServerClass extends UnicastRemoteObject implements MathServer{

    public MathServerClass() throws RemoteException {}

    public double average(List<Double> objetos) throws RemoteException {

        double somatorio = 0;
        for (Double d : objetos) {
            somatorio += d;
        }
        return somatorio / objetos.size();
    }

    public double desvio(List<Double> objetos) throws RemoteException {

        double media = average(objetos);
        double somatorio = 0;
        for (int i=0; i < objetos.size(); i++){
            double res = objetos.get(i) - media;
            somatorio = somatorio + res * res;
        }
        return Math.sqrt(((double) 1 / (objetos.size()-1)) * somatorio);
    }

    public double minimo(List<Double> objetos) throws RemoteException {

        double minIndex = Collections.min(objetos);
        return minIndex;
    }

    public double maximo(List<Double> objetos) throws RemoteException {

        double maxIndex = Collections.max(objetos);
        return maxIndex;
    }
}
