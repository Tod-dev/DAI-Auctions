import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import lights.*;
import lights.interfaces.*;
//import lights.interfaces.ITupleSpace;
//import lights.interfaces.TupleSpaceException;

public class RemoteTupleSpace extends UnicastRemoteObject implements IRemoteTupleSpace{

	private static final long serialVersionUID = 1L;
	
	private ITupleSpace space;

	protected RemoteTupleSpace(String name) throws RemoteException {
		super();

		this.space = new TupleSpace(name);
	}
		

	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return this.space.getName();
	}


	@Override
	public void out(ITuple tuple) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		this.space.out(tuple);
	}


	@Override
	public void outg(ITuple[] tuples) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		this.space.outg(tuples);
	}


	@Override
	public ITuple in(ITuple template) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		return this.space.in(template);
	}


	@Override
	public ITuple inp(ITuple template) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		return this.space.inp(template);
	}


	@Override
	public ITuple[] ing(ITuple template) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		return this.space.ing(template);
	}


	@Override
	public ITuple rd(ITuple template) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		return this.space.rd(template);
	}


	@Override
	public ITuple rdp(ITuple template) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		return this.space.rdp(template);
	}


	@Override
	public ITuple[] rdg(ITuple template) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		return this.space.rdg(template);
	}


	@Override
	public int count(ITuple template) throws RemoteException, TupleSpaceException {
		// TODO Auto-generated method stub
		return this.space.count(template);
	}
	
	
	
	public static void main(String[] args) throws Exception{
        String name;

		
                if (args.length ==  1)
                        name = args[0];
                else
                        name = "TupleSpace";

		IRemoteTupleSpace remoteTupleSpace = new RemoteTupleSpace(name);
		
		try {
          LocateRegistry.createRegistry(1099);
          System.out.println("Java RMI registry created.");
		} catch (RemoteException e) {
          System.out.println("Java RMI registry already exists.");
		}
		
        Naming.rebind("//localhost/" + name, remoteTupleSpace);
        System.out.println("Remote TupleSpace registered on //localhost:1099/"+name);
		
	}

	
}
