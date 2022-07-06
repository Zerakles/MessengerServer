package de.zerakles.main;

import de.zerakles.server.Server;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MessengerServer extends JavaPlugin {

    private MessengerServer messengerServerInstance;

    public MessengerServer getMessengerServerInstance() {
        return messengerServerInstance;
    }

    public void setMessengerServerInstance(MessengerServer messengerServerInstance) {
        this.messengerServerInstance = messengerServerInstance;
    }

    public Thread thread;
    public int port;
    public String address;
    public String name;

    public void onEnable(){
        setMessengerServerInstance(this);
        checkConf();
        thread = new Thread("EchoServer"){
            public void run(){
                Server server = new Server(port,address,name);
                server.start();
            }
        };
        thread.start();
        Bukkit.getConsoleSender().sendMessage("§8[§5Messenger§8] §aServer started!");
    }

    public void onDisable(){
        if(thread.isAlive()){
            thread.stop();
        }
        Bukkit.getConsoleSender().sendMessage("§8[§5Messenger§8] §cServer stopped!");
        return;
    }

    private void checkConf(){
        File file = new File("plugins/MessengerServer/conf.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if(file.exists()){
            port = fileConfiguration.getInt("server.port");
            address = fileConfiguration.getString("server.address");
            name = fileConfiguration.getString("server.name");
        } else {
            port = 2003;
            address = "localhost";
            name = "EchoServer";
            fileConfiguration.set("server.port", port);
            fileConfiguration.set("server.address", address);
            fileConfiguration.set("server.name", name);
            try {
                fileConfiguration.save(file);
                Bukkit.getConsoleSender().sendMessage("§8[§5Messenger§8] §aConfig saved!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
