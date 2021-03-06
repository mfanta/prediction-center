package cz.mfanta.tip_centrum.view.action;

import cz.mfanta.tip_centrum.entity.Team;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.entity.manager.ITeamManager;
import cz.mfanta.tip_centrum.service.gui.GuiService;
import cz.mfanta.tip_centrum.view.dialog.AddAliasDialog;
import cz.mfanta.tip_centrum.view.dialog.AddAliasDialogDesign;
import org.apache.commons.lang3.Validate;

import javax.swing.*;

public class AddAliasAction implements TipCentrumAction {

	private final JFrame mainFrame;

	private final ITeamManager teamManager;

	private final IFixtureManager fixtureManager;

	private final GuiService guiService;

	public AddAliasAction(JFrame mainFrame, ITeamManager teamManager, IFixtureManager fixtureManager, GuiService guiService) {
		this.mainFrame = mainFrame;
		this.teamManager = teamManager;
		this.fixtureManager = fixtureManager;
		this.guiService = guiService;
	}

	@Override
	public void performAction() {
		final AddAliasDialog addAliasDialog = new AddAliasDialog(mainFrame, AddAliasDialogDesign.DIALOG_TITLE);
		addAliasDialog.pack();
		addAliasDialog.setLocationRelativeTo(mainFrame);
		addAliasDialog.setVisible(true);
		if (addAliasDialog.wasOkPressed()) {
			final String teamName = addAliasDialog.getName();
			final String alias = addAliasDialog.getAlias();
			storeAlias(teamName, alias);
		}
	}

	private void storeAlias(String teamName, String teamAlias) {
		Validate.notEmpty(teamName);
		Validate.notEmpty(teamAlias);
		final Team team = teamManager.getTeamByName(teamName);
		if (team == null) {
			final Team aliasTeam = teamManager.getTeamByName(teamAlias);
			if (aliasTeam != null) {
				// 1. create a team 'teamName'
				final Team newTeam = teamManager.createTeam(teamName);
				// 2. change references to 'teamAlias' team to 'teamName' team
				fixtureManager.updateTeamInFixtures(aliasTeam, newTeam);
				// 3. delete 'teamAlias' team
				teamManager.deleteTeam(aliasTeam);
				// 4. create 'teamName'=>'teamAlias' team alias
				teamManager.createTeamAlias(teamName, teamAlias);
				guiService.updateFixtures();
			} else {
				throw new RuntimeException("Neither team name " + teamName + " nor team alias " + teamAlias + " does correspond to an existing team");
			}
		} else {
			teamManager.createTeamAlias(teamName, teamAlias);
			guiService.updateFixtures();
		}
	}

}
