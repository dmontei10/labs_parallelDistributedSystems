import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Cliente {

    private MathServer mathServer;
    public Cliente() {
        try {

            Registry reg = LocateRegistry.getRegistry("servidor", 1099);

            mathServer = (MathServer) reg.lookup("rmi://servidor/MathServer1");

        } catch (NotBoundException e) {
            e.printStackTrace();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public double average(List<Double> objetos) throws RemoteException {
        return mathServer.average(objetos);
    }

    public double desvio(List<Double> objetos) throws RemoteException {
        return mathServer.desvio(objetos);
    }

    public double minimo(List<Double> objetos) throws RemoteException {
        return mathServer.minimo(objetos);
    }

    public double maximo(List<Double> objetos) throws RemoteException {
        return mathServer.maximo(objetos);
    }
}
