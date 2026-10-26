package me.mozarez.aurora.minetech

import org.bukkit.plugin.java.JavaPlugin

class MineTech : JavaPlugin() {

    companion object {
        internal lateinit var instance: MineTech private set
    }

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {

    }

    override fun onDisable() {

    }
}
