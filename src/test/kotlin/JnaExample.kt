/*
 * Copyright (c) 2017 Arnaud 'Bluexin' Solé
 *
 * This file is part of drpc4k.
 *
 * drpc4k is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * drpc4k is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with drpc4k.  If not, see <http://www.gnu.org/licenses/>.
 */

import be.bluexin.drpc4k.jna.DiscordRichPresence
import be.bluexin.drpc4k.jna.RPCHandler
import java.util.*

/**
 * Part of drpc4k by Bluexin, released under GNU GPLv3.
 *
 * @author Bluexin
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Missing Client ID")
        return
    }

    RPCHandler.onErrored = { errorCode, message -> System.err.println("$errorCode = $message") }
    RPCHandler.onDisconnected = { errorCode, message -> println("${if (errorCode != 0) "$errorCode = " else ""}$message") }

    RPCHandler.connect(args[0])

    val presence = DiscordRichPresence {
        details = "Raid: Kill Migas"
        state = "Recruiting"
        partyId = "Awesome Party ID"
        partySize = Random().nextInt(20) + 1
        partyMax = 24
        setDuration(1200L)
        smallImageKey = "ia_sakura_water"
        largeImageKey = "ia_sakura_water"
        smallImageText = "OwO smol"
        largeImageText = "OwO big"
        joinSecret = "anawesomesecret"
        spectateSecret = "anawesomesecret2"
    }

    RPCHandler.ifConnectedOrLater {
        RPCHandler.updatePresence(presence)
    }

    println("Starting to sleep...")
    Thread.sleep(120000)

    println("Done, disconnecting")
    if (RPCHandler.connected.get()) RPCHandler.disconnect()

    RPCHandler.finishPending()
}