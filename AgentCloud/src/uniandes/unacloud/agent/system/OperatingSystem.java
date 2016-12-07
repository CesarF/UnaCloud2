package uniandes.unacloud.agent.system;

import java.io.File;

import uniandes.unacloud.agent.exceptions.UnsupportedCommandException;
import uniandes.unacloud.common.utils.LocalProcessExecutor;
import uniandes.unacloud.common.utils.UnaCloudConstants;

/**
 * @author Eduardo Rosales 
 * @author CesarF
 * Responsible for executing operating system operations
 */
public abstract class OperatingSystem {
	

    public static final String PATH_SEPARATOR = File.separator;
    public static final String BIN="bin";

    @SuppressWarnings("unused")
	private String operatingSystemName = getOperatingSystemName();
	private String operatingArchitecture = getOperatingSystemArchitect();
	@SuppressWarnings("unused")
	private String operatingSystemVersion = getOperatingSystemVersion();
	private String hostname;
	
    /**
     * Responsible for turning off the local operating system
     * @return result for process
     */
    public String turnOff() throws UnsupportedCommandException{
    	new Thread(){
         	public void run() {
         		try {
					Thread.sleep(1000);
		            LocalProcessExecutor.executeCommand(getTurnOffCommand());
				} catch (Exception e) {
					e.printStackTrace();
				}
         	};
         }.start();
         return UnaCloudConstants.SUCCESSFUL_OPERATION;
    }

    /**
     * Responsible for returning the command to turn off host machine
     * @return
     */
	public abstract String getTurnOffCommand() throws UnsupportedCommandException;
		

    /**
     * Responsible for restarting the local operating system
     * @return result for restart process
     */
    public String restart() throws UnsupportedCommandException{    	
    	new Thread(){
         	public void run() {
         		try {
					Thread.sleep(1000);
					executeCommandOS(getRestartCommand());
				} catch (Exception e) {
					e.printStackTrace();
				}
         	};
         }.start();
         return UnaCloudConstants.SUCCESSFUL_OPERATION;
    }
        
    /**
     * Responsible for returning the command to restart host machine
     * @return
     */
	public abstract String getRestartCommand() throws UnsupportedCommandException;

    /**
     * Responsible for logout the local operating system
     * @return result for log out process
     * @throws UnsupportedCommandException 
     */
    public String logOut() throws UnsupportedCommandException {
    	new Thread(){
         	public void run() {
         		try {
					Thread.sleep(1000);
					executeCommandOS(getLogOutCommand());
				} catch (Exception e) {
					e.printStackTrace();
				}
         	};
         }.start();
         return UnaCloudConstants.SUCCESSFUL_OPERATION;
    }
    
    /**
     * Used by other methods in class to execute commands in this OS.
     * @param command
     * @return result in console
     */
    protected String executeCommandOS(String command){
    	try {
        	return LocalProcessExecutor.executeCommandOutput(command);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * Responsible for setting priority for one process
     * @param processName
     */
    public abstract void setPriorityProcess(String processName) throws UnsupportedCommandException;
    
    
    /**
     * Responsible for returning the command to log out user from host machine
     * @return
     * @throws UnsupportedCommandException 
     */
	public abstract String getLogOutCommand() throws UnsupportedCommandException;
	
    /**
     * Returns user username currently logged in machine
     * @return username
     */
    public abstract String getUserName() throws UnsupportedCommandException;
    
    /**
     * Returns current user in system
     * @return String current user name
     */
    public abstract String getWhoAmI() throws UnsupportedCommandException;
    
    /**
     * Returns path of program data
     * @return
     * @throws UnsupportedCommandException
     */
    public abstract String getProgramDataPath() throws UnsupportedCommandException;
    
    /**
     * Responsible for obtaining the hostname
     * @return machine hostmname
     * @throws UnsupportedCommandException 
     */
    public String getHostname() throws UnsupportedCommandException {
    	if(hostname!=null)return hostname;
    	hostname=LocalProcessExecutor.executeCommandOutput(getHostNameCommand()).trim();
    	return hostname;
    }
    
    /**
     * Validates if agent is being executed by super user
     * @return true in case agent is being executed by super user, false in case not
     */
    public abstract boolean isRunningBySuperUser() throws UnsupportedCommandException;
    
    /**
     * Responsible for returning the command to get hostname from host machine
     * @return operation result
     * @throws UnsupportedCommandException 
     */
	public abstract String getHostNameCommand() throws UnsupportedCommandException;
	
    /**
     * Responsible for turning on other physical machines connected by lan 
     * @return operation result
     * @throws UnsupportedCommandException
     */
	public abstract String turnOnMachines(String[] message) throws UnsupportedCommandException;
		
	 /**
     * Responsible for obtaining the operating system name
     * @return os name
     */
    public static String getOperatingSystemName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * Responsible for obtaining the operating system version
     * @return oos version
     */
    public static String getOperatingSystemVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Responsible for obtaining the operating system architect
     * @return os architecture
     */
    public static String getOperatingSystemArchitect() {
        return System.getProperty("os.arch").toLowerCase();
    }
	
	
    /**
     * Responsible for validating if OS is windows
     */
    public static boolean isWindows() {		 
		return (getOperatingSystemName().indexOf("win") >= 0); 
	} 
    
    /**
     * Responsible for validating if OS is Mac
     */
	public static boolean isMac() { 
		return (getOperatingSystemName().indexOf("mac") >= 0); 
	} 
	
	/**
     * Responsible for validating if OS is Unix: Debian and Redhat families
     */
	public static boolean isUnix() { 
		String os = getOperatingSystemName();
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 ); 
	} 
	
	/**
     * Responsible for validating if OS is AIX
     */
	public static boolean isAix() { 
		return (getOperatingSystemName().indexOf("aix") > 0 ); 
	} 
	
	/**
     * Responsible for validating if OS is Solaris
     */
	public static boolean isSolaris() { 
		return (getOperatingSystemName().indexOf("sunos") >= 0); 
	}	
	
	/**
     * Responsible for validating if OS is FreeBSD
     */
	public static boolean isFreeBSD(){
		return (getOperatingSystemName().indexOf("free") >=0);
	}
	
	/**
     * Responsible for validating if OS is windows
     */	
	public static boolean isHpUx(){
		return (getOperatingSystemName().indexOf("hp") >=0);
	}
	
	/**
     * Responsible for validating if OS architecture is amd64
     */	
	public static boolean isAMD64(){
		return (getOperatingSystemName().indexOf("amd64") >= 0);
	}
	
	/**
     * Responsible for validating if OS architecture is x86
     */	
	public boolean isX86(){
		return (operatingArchitecture.indexOf("x86") >= 0);
	}
	
	/**
     * Responsible for validating if OS architecture is sparc
     */	
	public boolean isSparc(){
		return (operatingArchitecture.indexOf("sparc") >= 0);
	}
	
	/**
     * Responsible for validating if OS architecture is ia64
     */	
	public boolean isIA(){
		return (operatingArchitecture.indexOf("ia64") >=0);
	}
	
	/**
     * Responsible for validating if OS architecture is risc
     */	
	public boolean isPaRisc(){
		return (operatingArchitecture.indexOf("risc") >= 0);
	}
	
	/**
     * Responsible for validating if OS architecture is ppc
     */	
	public boolean isPpc(){
		return (operatingArchitecture.indexOf("ppc") >= 0);
	}
	
	/**
     * Responsible for validating if OS architecture is ppc64
     */	
	public boolean isPpc64(){
		return (operatingArchitecture.indexOf("ppc64") >= 0);
	}
	
	/**
     * Responsible for validating if OS architecture is sparc64
     */	
	public boolean isSparc64(){
		return (operatingArchitecture.indexOf("sparc64") >= 0);
	}
}
