package com.RSen.Commandr.core;

import android.content.Context;

import com.RSen.Commandr.builtincommands.BluetoothCommand;
import com.RSen.Commandr.builtincommands.BluetoothOffCommand;
import com.RSen.Commandr.builtincommands.ChatbotCommand;
import com.RSen.Commandr.builtincommands.FlashlightCommand;
import com.RSen.Commandr.builtincommands.FlashlightOffCommand;
import com.RSen.Commandr.builtincommands.GPSCommand;
import com.RSen.Commandr.builtincommands.GPSOffCommand;
import com.RSen.Commandr.builtincommands.LampshadeIOCommand;
import com.RSen.Commandr.builtincommands.NextMusicCommand;
import com.RSen.Commandr.builtincommands.PauseMusicCommand;
import com.RSen.Commandr.builtincommands.PlayPlaylistCommand;
import com.RSen.Commandr.builtincommands.PreviousMusicCommand;
import com.RSen.Commandr.builtincommands.ReadUnreadSMSCommand;
import com.RSen.Commandr.builtincommands.ResumeMusicCommand;
import com.RSen.Commandr.builtincommands.WifiCommand;
import com.RSen.Commandr.builtincommands.WifiOffCommand;
import com.RSen.Commandr.util.GoogleNowUtil;


/**
 * @author Ryan Senanayake
 *         Commandr for Google Now
 *         MostWantedCommand.java
 * @version 1.0
 *          5/28/14
 */
public class MostWantedCommands {
    private static MostWantedCommand[] commands;

    /**
     * Executes a matching MostWantedCommand
     *
     * @param context The context necessary to execute the command
     * @return true if matching command found, false otherwise.
     */
    public static MostWantedCommand[] getCommands(Context context) {
        if (commands == null) {
            commands = new MostWantedCommand[]{new FlashlightCommand(context), new FlashlightOffCommand(context),
                    new WifiCommand(context), new WifiOffCommand(context), new GPSCommand(context), new GPSOffCommand(context), new BluetoothCommand(context),
                    new BluetoothOffCommand(context), new PauseMusicCommand(context), new ResumeMusicCommand(context), new NextMusicCommand(context), new PreviousMusicCommand(context)
                    , new ReadUnreadSMSCommand(context), new PlayPlaylistCommand(context), new ChatbotCommand(context), new LampshadeIOCommand(context)};
        }
        return commands;
    }

    public static boolean execute(Context context, String phrase) {
        boolean commandExecuted = false;
        for (MostWantedCommand command : getCommands(context)) {
            if (command.isEnabled(context)) {
                String[] activationPhrases = command.getPhrase(context).split(",");
                for (String activationPhrase : activationPhrases) {
                    if (command.getPredicateHint() == null) {
                        if (activationPhrase.toLowerCase().trim().equals(phrase.toLowerCase().trim())) {
                            command.execute(context, "");
                            commandExecuted = true;
                            if (!command.isHandlingGoogleNowReset()) {
                                GoogleNowUtil.resetGoogleNow(context);
                            }
                        }
                    } else {
                        if (phrase.toLowerCase().trim().startsWith(activationPhrase.toLowerCase().trim())) {
                            command.execute(context, phrase.toLowerCase().trim().substring(activationPhrase.trim().length()).trim());
                            commandExecuted = true;
                            if (!command.isHandlingGoogleNowReset()) {
                                GoogleNowUtil.resetGoogleNow(context);
                            }
                        }
                    }
                }

            }
        }
        return commandExecuted;
    }

}