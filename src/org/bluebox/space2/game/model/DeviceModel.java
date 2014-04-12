package org.bluebox.space2.game.model;

import org.bluebox.space2.game.model.DeviceModel.Device;

public class DeviceModel {

	public static enum Device {
		PHASER_1,
		PHASER_2,
		PHASER_3,
		PHASER_4,
		PHASER_5,
		PHASER_6,
		PHASER_7,
		PHASER_8,
		PHASER_9,
		SHIELD_1,
		SHIELD_2,
		SHIELD_3,
		SHIELD_4,
		SHIELD_5,
		SHIELD_6,
		SHIELD_7,
		SHIELD_8,
		SHIELD_9,
		HULL_1,
		HULL_2,
		HULL_3,
		HULL_4,
		HULL_5,
		HULL_6,
		HULL_7,
		HULL_8,
		HULL_9,
		COLONIZER
	}
	
	private static final DeviceModel sDevices[] = {
		new DeviceModel(Device.PHASER_1,	"Phaser-1",	1,	1,	0),
		new DeviceModel(Device.PHASER_2,	"Phaser-2",	1,	2,	0),
		new DeviceModel(Device.PHASER_3,	"Phaser-3",	1,	3,	0),
		new DeviceModel(Device.PHASER_4,	"Phaser-4",	1,	4,	0),
		new DeviceModel(Device.PHASER_5,	"Phaser-5",	1,	5,	0),
		new DeviceModel(Device.PHASER_6,	"Phaser-6",	1,	6,	0),
		new DeviceModel(Device.PHASER_7,	"Phaser-7",	1,	7,	0),
		new DeviceModel(Device.PHASER_8,	"Phaser-8",	1,	8,	0),
		new DeviceModel(Device.PHASER_9,	"Phaser-9",	1,	9,	0),
		new DeviceModel(Device.SHIELD_1,	"Shield-1",	1,	0,	1),
		new DeviceModel(Device.COLONIZER,"Colonizer",10,0,	0),
		new DeviceModel(Device.HULL_1,	"Hull-1",	1,	0,	1)
	};

	public Device 	id;
	public String 	name;
	public int 		mass;
	public int 		attack;
	public int 		defense;

	public DeviceModel (Device id, String name, int mass, int attack, int defense) {
		this.id = id;
		this.name = name;
		this.mass = mass;
		this.attack = attack;
		this.defense = defense;
	}

	public static DeviceModel get (Device id) {
		for (DeviceModel device: sDevices) {
			if (device.id == id) {
				return device;
			}
		}
		return null;
	}

}
