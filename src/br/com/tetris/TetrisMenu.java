package br.com.tetris;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TetrisMenu {
    private final Color backgroundColor = Color.lightGray;
    private final Tetris game;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItemNew;
    private JMenuItem menuItemExit;

    public TetrisMenu(Tetris game) {
        this.game = game;
        initMenu();
    }

    private void initMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("Options");

        initMenuItemNew();
        menuItemExit = new JMenuItem("Exit");

        initMenuItemNew();
        initMenuItemExit();

        menu.add(menuItemNew);
        menu.add(menuItemExit);
        menuBar.add(menu);

        menuBar.setBackground(backgroundColor);

        game.add(menuBar, BorderLayout.NORTH);
    }

    private void initMenuItemNew() {
        menuItemNew = new JMenuItem("New");
        menuItemNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.restart();
            }
        });
    }

    private void initMenuItemExit() {
        menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.exit();
            }
        });
    }

}
