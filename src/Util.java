package code;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Util
{
    public static final JavaPlugin plugin = LightCatcher.getPlugin(LightCatcher.class);
    public static final Server server = plugin.getServer();
    public static final Logger logger = server.getLogger();
    public static final PluginManager manager = server.getPluginManager();

    public static void f(String m)
    {Util.logger.log(Level.INFO, "-|" + m + "|-");}
    
    public static void fw(String m)
    {Util.logger.log(Level.WARNING, "-|" + m + "|-");}
    
    public static void fe(String m)
    {Util.logger.log(Level.SEVERE, "-|" + m + "|-");}
}


