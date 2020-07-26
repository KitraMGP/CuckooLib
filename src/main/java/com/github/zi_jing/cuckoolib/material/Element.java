package com.github.zi_jing.cuckoolib.material;

public enum Element {
	H ("Hydrogen"     ,   1,   0),
	He("Helium"       ,   2,   2),
	Li("Lithium"      ,   3,   4),
	Be("Beryllium"    ,   4,   5),
	B ("Boron"        ,   5,   5),
	C ("Carbon"       ,   6,   6),
	N ("Nitrogen"     ,   7,   7),
	O ("Oxygen"       ,   8,   8),
	F ("Fluorine"     ,   9,   9),
	Ne("Neon"         ,  10,  10),
	Na("Sodium"       ,  11,  11),
	Mg("Magnesium"    ,  12,  12),
	Al("Aluminium"    ,  13,  13),
	Si("Silicon"      ,  14,  14),
	P ("Phosphor"     ,  15,  15),
	S ("Sulfur"       ,  16,  16),
	Cl("Chlorine"     ,  17,  18),
	Ar("Argon"        ,  18,  22),
	K ("Potassium"    ,  19,  20),
	Ca("Calcium"      ,  20,  20),
	Sc("Scandium"     ,  21,  24),
	Ti("Titanium"     ,  22,  26),
	V ("Vanadium"     ,  23,  28),
	Cr("Chrome"       ,  24,  28),
	Mn("Manganese"    ,  25,  30),
	Fe("Iron"         ,  26,  30),
	Co("Cobalt"       ,  27,  32),
	Ni("Nickel"       ,  28,  30),
	Cu("Copper"       ,  29,  34),
	Zn("Zinc"         ,  30,  35),
	Ga("Gallium"      ,  31,  39),
	Ge("Germanium"    ,  32,  40),
	As("Arsenic"      ,  33,  42),
	Se("Selenium"     ,  34,  45),
	Br("Bromine"      ,  35,  45),
	Kr("Krypton"      ,  36,  48),
	Rb("Rubidium"     ,  37,  48),
	Sr("Strontium"    ,  38,  49),
	Y ("Yttrium"      ,  39,  50),
	Zr("Zirconium"    ,  40,  51),
	Nb("Niobium"      ,  41,  53),
	Mo("Molybdenum"   ,  42,  53),
	Tc("Technetium"   ,  43,  55),
	Ru("Ruthenium"    ,  44,  57),
	Rh("Rhodium"      ,  45,  58),
	Pd("Palladium"    ,  46,  60),
	Ag("Silver"       ,  47,  60),
	Cd("Cadmium"      ,  48,  64),
	In("Indium"       ,  49,  65),
	Sn("Tin"          ,  50,  68),
	Sb("Antimony"     ,  51,  70),
	Te("Tellurium"    ,  52,  75),
	I ("Iodine"       ,  53,  74),
	Xe("Xenon"        ,  54,  77),
	Cs("Caesium"      ,  55,  77),
	Ba("Barium"       ,  56,  81),
	La("Lantanium"    ,  57,  81),
	Ce("Cerium"       ,  58,  82),
	Pr("Praseodymium" ,  59,  81),
	Nd("Neodymium"    ,  60,  84),
	Pm("Promethium"   ,  61,  83),
	Sm("Samarium"     ,  62,  88),
	Eu("Europium"     ,  63,  88),
	Gd("Gadolinium"   ,  64,  93),
	Tb("Terbium"      ,  65,  93),
	Dy("Dysprosium"   ,  66,  96),
	Ho("Holmium"      ,  67,  97),
	Er("Erbium"       ,  68,  99),
	Tm("Thulium"      ,  69,  99),
	Yb("Ytterbium"    ,  70, 103),
	Lu("Lutetium"     ,  71, 103),
	Hf("Hafnium"      ,  72, 106),
	Ta("Tantalum"     ,  73, 107),
	W ("Wolframium"   ,  74, 109),
	Re("Rhenium"      ,  75, 111),
	Os("Osmium"       ,  76, 114),
	Ir("Iridium"      ,  77, 115),
	Pt("Platinum"     ,  78, 117),
	Au("Gold"         ,  79, 117),
	Hg("Mercury"      ,  80, 120),
	Tl("Thallium"     ,  81, 123),
	Pb("Lead"         ,  82, 125),
	Bi("Bismuth"      ,  83, 125),
	Po("Polonium"     ,  84, 124),
	At("Astatine"     ,  85, 124),
	Rn("Radon"        ,  86, 134),
	Fr("Francium"     ,  87, 134),
	Ra("Radium"       ,  88, 136),
	Ac("Actinium"     ,  89, 136),
	Th("Thorium"      ,  90, 140),
	Pa("Protactinium" ,  91, 138),
	U ("Uranium"      ,  92, 146),
	Np("Neptunium"    ,  93, 144),
	Pu("Plutonium"    ,  94, 152),
	Am("Americium"    ,  95, 150),
	Cm("Curium"       ,  96, 153),
	Bk("Berkelium"    ,  97, 152),
	Cf("Californium"  ,  98, 153),
	Es("Einsteinium"  ,  99, 153),
	Fm("Fermium"      , 100, 157),
	Md("Mendelevium"  , 101, 157),
	No("Nobelium"     , 102, 157),
	Lr("Lawrencium"   , 103, 159),
	Rf("Rutherfordium", 104, 161),
	Db("Dubnium"      , 105, 163),
	Sg("Seaborgium"   , 106, 165),
	Bh("Bohrium"      , 107, 163),
	Hs("Hassium"      , 108, 169),
	Mt("Meitnerium"   , 109, 167),
	Ds("Darmstadtium" , 110, 171),
	Rg("Roentgenium"  , 111, 169),
	Cn("Copernicium"  , 112, 173),
	Nh("Nihonium"     , 113, 171),
	Fl("Flerovium"    , 114, 175),
	Mc("Moscovium"    , 115, 173),
	Lv("Livermorium"  , 116, 177),
	Ts("Tennessine"   , 117, 177),
	Og("Oganesson"    , 118, 176);

	private String name;
	private int proton, neutron;

	Element(String name, int proton, int neutron) {
		this.name = name;
		this.proton = proton;
		this.neutron = neutron;
	}

	public String getName() {
		return this.name;
	}

	public int getProton() {
		return this.proton;
	}

	public int getNeutron() {
		return this.neutron;
	}

	public int getRelativeMass() {
		return this.proton + this.neutron;
	}
}