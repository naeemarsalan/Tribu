package samp20.zombiesurvival;


import org.bukkit.ChatColor;

public class Constants {
	// 20 ticks = 1 second
	public static final int TickDelay = 1;
	public static final int SpawnDelay = 20;
	public static final int NextRoundDelay = 20 * 10;
	public static final byte LevelFileVersion = 1;
	public static final int VoteDelay = 20 * 30;
	public static final String levelFolder = "plugins/zombie_survival/levels";
	//public static final String levelFolder = "plugins"+File.separatorChar+"zombie_survival"+File.separatorChar+"levels";
	//public static final String[] levelFolders = {"plugins","zombie_survival","levels"};
	// i18n
	// English
	
	public static final String SignBuy = "buy";
	public static final String MessageInvalidVote = ChatColor.RED + "Invalid vote";
	public static final String MessageThankyouForYourVote=ChatColor.GREEN + "Thankyou for your vote";
	public static final String MessageYouCannotVoteAtThisTime=ChatColor.RED + "You cannot vote at this time";
	public static final String MessageLevelLoadedSuccessfully = ChatColor.GREEN + "Level loaded successfully";
	public static final String MessageLevelIsAlreadyTheCurrentLevel = ChatColor.RED + "Level %s is already the current level";
	public static final String MessageUnableToSaveLevel = ChatColor.RED	+ "Unable to save level, try again later";
	public static final String MessageUnableToLoadLevel = ChatColor.RED	+ "Unable to load level";
	public static final String MessageNoLevelLoaded = "No level loaded, type '/level load' to load one,";
	public static final String MessageNoLevelLoaded2 = "or '/level create' to create a new one,";
	public static final String MessageTeleportedToDeathSpawn=ChatColor.GREEN + "Teleported to death spawn";
	public static final String MessageDeathSpawnSet = "Death spawn set.";
	public static final String MessageTeleportedToInitialSpawn =ChatColor.GREEN + "Teleported to initial spawn";
	public static final String MessageInitialSpawnSet =ChatColor.GREEN + "Initial spawn set.";
	public static final String MessageUnableToSaveCurrentLevel = ChatColor.RED	+ "Unable to save current level.";
	public static final String MessageLevelSaveSuccessful = ChatColor.GREEN	+ "Level save successful";
	public static final String MessageLevelCreated= ChatColor.GREEN + "Level " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " created";
	public static final String MessageUnableToDeleteLevel = ChatColor.RED	+ "Unable to delete current level.";
	public static final String MessageLevelDeleted = ChatColor.GREEN	+ "Level deleted successfully.";
	public static final String MessageLevels= ChatColor.GREEN + "Levels : %s";
	public static final String MessageZombieModeEnabled= "Zombie Mode enabled!";
	public static final String MessageZombieModeDisabled= "Zombie Mode disabled!";
	public static final String MessageSpawnpointAdded= ChatColor.GREEN + "Spawnpoint added";
	public static final String MessageSpawnpointRemoved= ChatColor.GREEN + "Spawnpoint removed";
	public static final String MessageInvalidSpawnName= ChatColor.RED + "Invalid spawn name";
	public static final String MessageTeleportedToZombieSpawn = ChatColor.GREEN + "Teleported to zombie spawn "+ ChatColor.LIGHT_PURPLE + "%s";
	public static final String MessageUnableToGiveYouThatItem = "Unable to give you that item...";
	public static final String MessagePurchaseSuccessfulMoney = ChatColor.GREEN + "Purchase successful. Money: "+ ChatColor.DARK_PURPLE + "%s $";
	public static final String MessageYouDontHaveEnoughMoney =	ChatColor.DARK_RED	+ "You don't have enough money for that!";
	public static final String MessageMoneyPoints = ChatColor.GREEN + "Money: " + ChatColor.DARK_PURPLE	+ "%s $" + ChatColor.GREEN + " Points: " + ChatColor.RED + "%s";
	public static final String MessageGameInProgress=ChatColor.GREEN+ "Game in progress, you will spawn next round";
	public static final String MessageZombieHavePrevailed =	ChatColor.GREEN + "Zombies have prevailed!";
	public static final String MessageYouHaveReachedWave = 	ChatColor.GREEN + "You have reached wave "+ ChatColor.LIGHT_PURPLE+ "%s";
	public static final String BroadcastMapChosen=ChatColor.GREEN + "Map " + ChatColor.LIGHT_PURPLE +"%s" + ChatColor.GREEN + " has been chosen";
	public static final String BroadcastMapVoteStarting=ChatColor.GREEN + "Map vote starting,";
	public static final String BroadcastType = ChatColor.GREEN + "Type ";
	public static final String BroadcastSlashVoteForMap = ChatColor.GOLD + "'/vote %s'" + ChatColor.GREEN + " for map "	+ ChatColor.LIGHT_PURPLE + "%s";
	public static final String BroadcastVoteClosingInSeconds=ChatColor.GREEN + "Vote closing in " + ChatColor.LIGHT_PURPLE+ "%s" + ChatColor.GREEN + " seconds";
	public static final String BroadcastStartingWave= ChatColor.GREEN + "Starting wave " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + ", " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN	+ " Zombies @ " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " health";
	public static final String BroadcastWave = ChatColor.GREEN + "Wave " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " starting in "+ ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " seconds.";
	public static final String BroadcastWaveComplete = ChatColor.GREEN + "Wave Complete";
	public static final String InfoLevelFound= "%s levels found";
	public static final String InfoEnable = "Starting ZombieSurvival";
	public static final String InfoDisable = "Stopping ZombieSurvival";
	public static final String InfoLevelSaved = "Level saved";
	public static final String InfoLevelFolderDoesntExist ="Level folder doesn't exist";
	public static final String WarningUnableToSaveLevel = "Unable to save level";
	public static final String WarningThisCommandCannotBeUsedFromTheConsole= "This command cannot be used from the console";
	public static final String WarningIOErrorOnFileDelete = "IO error on file delete";
	public static final String SevereWorldInvalidFileVersion ="World invalid file version";
	public static final String SevereWorldDoesntExist = "World doesn't exist";
	public static final String SevereException = "Exception: %s";
	
	// French
	/*
	public static final String SignBuy = "acheter";
	public static final String MessageInvalidVote = ChatColor.RED + "Vote invalide";
	public static final String MessageThankyouForYourVote=ChatColor.GREEN + "Merci d'avoir voté";
	public static final String MessageYouCannotVoteAtThisTime=ChatColor.RED + "Vous ne pouvez pas voter pour le moment";
	public static final String MessageLevelLoadedSuccessfully = ChatColor.GREEN + "Niveau chargé avec succès";
	public static final String MessageLevelIsAlreadyTheCurrentLevel = ChatColor.RED + "Le niveau %s est déjà le niveau actuel.";
	public static final String MessageUnableToSaveLevel = ChatColor.RED	+ "Impossible de sauvegarder le niveau, essayer plus tard";
	public static final String MessageUnableToLoadLevel = ChatColor.RED	+ "Impossible de charger le niveau";
	public static final String MessageNoLevelLoaded = "Aucun niveau chargé, tapez '/level load' pour en charger un,";
	public static final String MessageNoLevelLoaded2 = "ou '/level create' pour en créer un nouveau.";
	public static final String MessageTeleportedToDeathSpawn=ChatColor.GREEN + "Téléporté au point de résurrection";
	public static final String MessageDeathSpawnSet = "Point de résurrection réglé.";
	public static final String MessageTeleportedToInitialSpawn =ChatColor.GREEN + "Téléporté au point de départ";
	public static final String MessageInitialSpawnSet =ChatColor.GREEN + "Point de départ réglé.";
	public static final String MessageUnableToSaveCurrentLevel = ChatColor.RED	+ "Impossible de sauvegarder le niveau actuel.";
	public static final String MessageLevelSaveSuccessful = ChatColor.GREEN	+ "Niveau sauvegardé avec succès";
	public static final String MessageLevelCreated= ChatColor.GREEN + "Le niveau " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " a été crée";
	public static final String MessageUnableToDeleteLevel = ChatColor.RED	+ "Impossible de supprimer le niveau actuel.";
	public static final String MessageLevelDeleted = ChatColor.GREEN	+ "Le niveau a été supprimé avec succès.";
	public static final String MessageLevels= ChatColor.GREEN + "Niveaux : %s";
	public static final String MessageZombieModeEnabled= "Mode survie de zombie activé !";
	public static final String MessageZombieModeDisabled= "Mode survie de zombie désactivé !";
	public static final String MessageSpawnpointAdded= ChatColor.GREEN + "Point d'apparition ajouté";
	public static final String MessageSpawnpointRemoved= ChatColor.GREEN + "Point d'apparition supprimé";
	public static final String MessageInvalidSpawnName= ChatColor.RED + "Nom du point invalide";
	public static final String MessageTeleportedToZombieSpawn = ChatColor.GREEN + "Téléporté au point d'apparition de zombie "+ ChatColor.LIGHT_PURPLE + "%s";
	public static final String MessageUnableToGiveYouThatItem = "Impossible de vous donner cet objet...";
	public static final String MessagePurchaseSuccessfulMoney = ChatColor.GREEN + "Achat effectué. Argent restant : "+ ChatColor.DARK_PURPLE + "%s $";
	public static final String MessageYouDontHaveEnoughMoney =	ChatColor.DARK_RED	+ "Vous n'avez pas assez d'argent pour ça !";
	public static final String MessageMoneyPoints = ChatColor.GREEN + "Argent : " + ChatColor.DARK_PURPLE	+ "%s $" + ChatColor.GREEN + " Points: " + ChatColor.RED + "%s";
	public static final String MessageGameInProgress=ChatColor.GREEN+ "Le jeu est en cours, vous apparaîtrez au prochain tour.";
	public static final String MessageZombieHavePrevailed =	ChatColor.GREEN + "Les zombies vous ont dominés !";
	public static final String MessageYouHaveReachedWave = 	ChatColor.GREEN + "Vous avez atteint la vague numéro "+ ChatColor.LIGHT_PURPLE+ "%s";
	public static final String BroadcastMapChosen=ChatColor.GREEN + "Le niveau " + ChatColor.LIGHT_PURPLE +"%s" + ChatColor.GREEN + " a été choisi";
	public static final String BroadcastMapVoteStarting=ChatColor.GREEN + "Début du vote pour le niveau,";
	public static final String BroadcastType = ChatColor.GREEN + "Taper ";
	public static final String BroadcastSlashVoteForMap = ChatColor.GOLD + "'/vote %s'" + ChatColor.GREEN + " pour le niveau "	+ ChatColor.LIGHT_PURPLE + "%s";
	public static final String BroadcastVoteClosingInSeconds=ChatColor.GREEN + "Les votes se termineront dans " + ChatColor.LIGHT_PURPLE+ "%s" + ChatColor.GREEN + " secondes";
	public static final String BroadcastStartingWave= ChatColor.GREEN + "La vague numéro " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " arrive, " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN	+ " zombies @ " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " points de vie";
	public static final String BroadcastWave = ChatColor.GREEN + "La vague numéro " + ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " arrivera dans "+ ChatColor.LIGHT_PURPLE + "%s" + ChatColor.GREEN + " secondes.";
	public static final String BroadcastWaveComplete = ChatColor.GREEN + "Vague terminée";
	public static final String InfoLevelFound= "%s niveaux trouvés";
	public static final String InfoEnable = "Activation de ZombieSurvival";
	public static final String InfoDisable = "Désactivation de ZombieSurvival";
	public static final String InfoLevelSaved = "Niveau sauvegardé";
	public static final String InfoLevelFolderDoesntExist ="Le répretoire des niveaux n'existe pas";
	public static final String WarningUnableToSaveLevel = "Impossible de sauvegarder le niveau";
	public static final String WarningThisCommandCannotBeUsedFromTheConsole= "Cette commande ne peut pas être utilisé à la console.";
	public static final String WarningIOErrorOnFileDelete = "Erreur E/S à la suppression du fichier";
	public static final String SevereWorldInvalidFileVersion ="Version du fichier de monde invalide";
	public static final String SevereWorldDoesntExist = "Le monde n'existe pas";
	public static final String SevereException = "Exception : %s";
	*/
}
