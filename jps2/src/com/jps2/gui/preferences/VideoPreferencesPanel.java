package com.jps2.gui.preferences;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NORTHWEST;

import java.awt.DisplayMode;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jps2.util.ResourceManager;
import com.jps2.util.SystemInfo;

public class VideoPreferencesPanel extends JPanel {

	private static final Insets	EMPTY_INSETS	= new Insets(0, 0, 0, 0);

	public VideoPreferencesPanel() {
		super(new GridBagLayout());
		initComponents();
	}

	private final void initComponents() {
		add(new JLabel(ResourceManager.getString("preferences.video.plugin")), new GridBagConstraints(0, 0, 1, 1, 0, 0, NORTHWEST, HORIZONTAL, EMPTY_INSETS, 0, 0));

		add(new JLabel(ResourceManager.getString("preferences.video.fullscreen.resolution")), new GridBagConstraints(0, 1, 1, 1, 0, 0, NORTHWEST, HORIZONTAL, EMPTY_INSETS, 0, 0));

		// make avaliable modes map
		final Map<String, Set<Integer>> resolutions = new HashMap<String, Set<Integer>>();

		for (final DisplayMode mode : SystemInfo.getValidDisplayModes()) {
			final String res = mode.getWidth() + "x" + mode.getHeight();

			Set<Integer> refreshRate = resolutions.get(res);

			if (refreshRate == null) {
				refreshRate = new HashSet<Integer>();
				resolutions.put(res, refreshRate);
			}
			if (mode.getRefreshRate() > 0) {
				refreshRate.add(mode.getRefreshRate());
			}
		}

		final String[] resArray = resolutions.keySet().toArray(new String[resolutions.size() + 1]);
		resArray[resArray.length - 1] = "";
		final List<String> resOrdened = Arrays.asList(resArray);

		Collections.sort(resOrdened, new Comparator<String>() {

			@Override
			public int compare(final String res1, final String res2) {
				if (res1.isEmpty() || res2.isEmpty()) {
					return +1;
				}
				final String[] tmp1 = res1.split("x");
				final String[] tmp2 = res2.split("x");
				return (Integer.parseInt(tmp1[0]) + Integer.parseInt(tmp1[1])) - (Integer.parseInt(tmp2[0]) + Integer.parseInt(tmp2[1]));
			}
		});

		final JComboBox resolution = new JComboBox(resOrdened.toArray());
		final JComboBox refreshRateDepth = new JComboBox();
		refreshRateDepth.setEnabled(false);
		resolution.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					final DefaultComboBoxModel model = ((DefaultComboBoxModel) refreshRateDepth.getModel());
					model.removeAllElements();
					if (e.getItem().equals("")) {
						refreshRateDepth.setEnabled(false);

					} else {
						model.addElement("default");
						final List<Integer> rateList = Arrays.asList(resolutions.get(e.getItem()).toArray(new Integer[resolutions.get(e.getItem()).size()]));
						Collections.sort(rateList);
						for (final Integer rate : rateList) {
							model.addElement(rate + "Hz");
						}
						refreshRateDepth.setEnabled(true);
					}
				}
			}
		});

		add(resolution, new GridBagConstraints(1, 1, 3, 1, 0, 0, NORTHWEST, HORIZONTAL, EMPTY_INSETS, 0, 0));
		add(refreshRateDepth, new GridBagConstraints(4, 1, 1, 1, 0, 0, NORTHWEST, HORIZONTAL, EMPTY_INSETS, 0, 0));
	}
}
