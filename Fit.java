// Adding processes to main memory depending on its fit

import java.util.*;

public class Fit {
	public static String[] mainMemory = new String[25];
	public static Scanner sc = new Scanner(System.in);
	public static int choose = 0;
	public static ArrayList<Integer> id = new ArrayList<Integer>();
	public static ArrayList<Integer> size = new ArrayList<Integer>();
	public static ArrayList<Integer> startingLocation = new ArrayList<Integer>();
	public static int numOfHoles = 0, sizePerHole = 0, min = 0, max = 0, minIndex = 0, maxIndex = 0;
	public static boolean insideHole = false;
	public static ArrayList<Integer> sizeOfHole = new ArrayList<Integer>();

	public static void main(String[] args) {
		String[] choices = {"Add Process (First Fit)", "Add Process (Best Fit)", "Add Process (Worst Fit)", "Remove Process", "Exit"};

		for (int x = 0; x < mainMemory.length; x++)
			mainMemory[x] = "-";

		while (true) {
			System.out.println("\nMAIN MENU:");
			for (int x = 1; x <= choices.length; x++)
				System.out.println(x + ". " + choices[x - 1]);

			System.out.println("\nMAIN MEMORY:");
			for (int x = 0; x < mainMemory.length; x++)
				System.out.print(mainMemory[x]);

			System.out.print("\n\nChoose: ");
			choose = sc.nextInt();

			if (choose == 1) {
				System.out.println("\nNew Process (First Fit)");
				enterIdAndSize();
				addFirstFit();
			} else if (choose == 2) {
				System.out.println("\nNew Process (Best Fit)");
				enterIdAndSize();
				bestAndWorst();
			} else if (choose == 3) {
				System.out.println("\nNew Process (Worst Fit)");
				enterIdAndSize();
				bestAndWorst();
			} else if (choose == 4) {
				System.out.println("\nRemove Process");
				removeProcess();
			} else if (choose == 5) {
				System.exit(0);
			}
		}
	}

	public static void enterIdAndSize() {
		System.out.print("ID: ");
		id.add(sc.nextInt());

		System.out.print("Size: ");
		size.add(sc.nextInt());
	}

	public static void addFirstFit() {
		int checkIfFit = 0;

		for (int x = 0; x < mainMemory.length; x++) {
			if (mainMemory[x] == "-") {
				if (checkIfFit != size.get(size.size() - 1))
					checkIfFit++;

				if (checkIfFit == size.get(size.size() - 1)) {
					startingLocation.add(x - size.get(size.size() - 1) + 1);
					break;
				}		
			}
		}
		if (checkIfFit < size.get(size.size() - 1)) {
			System.out.println("\nERROR: Size no longer fit");
			id.remove(id.size() - 1);			
			size.remove(size.size() - 1);
		}

		for (int x = startingLocation.get(startingLocation.size() - 1); x < startingLocation.get(startingLocation.size() - 1) + size.get(size.size() - 1); x++)
			mainMemory[x] = "#";
	}

	public static void bestAndWorst() {
		int base = 0;

		for (int x = 0; x < mainMemory.length; x++) {
			if (insideHole == false && mainMemory[x] == "#") {
				sizePerHole = 0;
				continue;
			} else if (insideHole == false && mainMemory[x] == "-") {
				insideHole = true;
				numOfHoles++;
				sizePerHole++;
			} else if (insideHole == true && mainMemory[x] == "-") {
				sizePerHole++;
				
				if (x == mainMemory.length - 1) {
					sizeOfHole.add(sizePerHole);
				} else {
					continue;
				}
			} else if (insideHole == true && mainMemory[x] == "#") {
				insideHole = false;
				sizeOfHole.add(sizePerHole);
				sizePerHole = 0;
			}
		}
		insideHole = false;
		numOfHoles = 0;
		min = sizeOfHole.get(0);
		max = sizeOfHole.get(0);

		for (int x = 0; x < sizeOfHole.size(); x++) {
			if (sizeOfHole.get(x) < min) {
				min = sizeOfHole.get(x);
				minIndex = x;
			}
			if (sizeOfHole.get(x) > max) {
				max = sizeOfHole.get(x);
				maxIndex = x;
			}
		}

		if (choose == 2) {
			if (size.get(size.size() - 1) <= min) {
				for (int x = 0; x < mainMemory.length; x++) {
					if (insideHole == false && mainMemory[x] == "-") {
						insideHole = true;
						numOfHoles++;
					} else if (insideHole == true && mainMemory[x] == "#") {
						insideHole = false;
					}

					if (mainMemory[x] == "-" && numOfHoles == minIndex + 1) {
						startingLocation.add(x);
						break;
					}
				}
			} else {
				System.out.println("\nERROR: Size no longer fit");
				id.remove(id.size() - 1);			
				size.remove(size.size() - 1);
			}
		} else if (choose == 3) {
			if (size.get(size.size() - 1) <= max) {
				for (int x = 0; x < mainMemory.length; x++) {
					if (insideHole == false && mainMemory[x] == "-") {
						insideHole = true;
						numOfHoles++;
					} else if (insideHole == true && mainMemory[x] == "#") {
						insideHole = false;
					}

					if (mainMemory[x] == "-" && numOfHoles == maxIndex + 1) {
						startingLocation.add(x);
						break;
					}
				}
			} else {
				System.out.println("\nERROR: Size no longer fit");
				id.remove(id.size() - 1);			
				size.remove(size.size() - 1);
			}
		}

		for (int x = startingLocation.get(startingLocation.size() - 1); x < startingLocation.get(startingLocation.size() - 1) + size.get(size.size() - 1); x++)
			mainMemory[x] = "#";

		insideHole = false;
		numOfHoles = 0;
		sizePerHole = 0;
		min = 0;
		max = 0;
		minIndex = 0;
		maxIndex = 0;
		sizeOfHole.clear();
	}

	public static void removeProcess() {
		System.out.print("Enter ID of process to remove: ");
		int index = id.indexOf(sc.nextInt());

		for (int x = startingLocation.get(index); x < startingLocation.get(index) + size.get(index); x++)
			mainMemory[x] = "-";

		id.remove(index);
		size.remove(index);
		startingLocation.remove(index);
	}
}