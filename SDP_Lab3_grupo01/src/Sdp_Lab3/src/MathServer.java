import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MathServer extends Remote {

    public double average(List<Double> objetos) throws RemoteException;
    public double desvio(List<Double> objetos) throws RemoteException;
    public double minimo(List<Double> objetos) throws RemoteException;
    public double maximo(List<Double> objetos) throws RemoteException;
}
