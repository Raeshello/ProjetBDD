package bdd;

import java.io.*;
import java.util.TreeSet;

/**
 * Classe qui contient des outils de sérialization
 *
 * @author Jason Mahdjoub
 * @version 1.0
 */
class SerializationTools {
	/**
	 * Serialise/binarise l'objet passé en paramètre pour retourner un tableau binaire
	 * @param o l'objet à serialiser
	 * @return the tableau binaire
	 * @throws IOException si un problème d'entrée/sortie se produit
	 */
	static byte[] serialize(Serializable o) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			oos.flush();
			byte[] tabByte = bos.toByteArray();
			return tabByte;
		} catch (IOException exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			try	{
				bos.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

	}

	/**
	 * Désérialise le tableau binaire donné en paramètre pour retrouver l'objet initial avant sa sérialisation
	 * @param data le tableau binaire
	 * @return l'objet désérialisé
	 * @throws IOException si un problème d'entrée/sortie se produit
	 * @throws ClassNotFoundException si un problème lors de la déserialisation s'est produit
	 */
	static Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Object o = in.readObject();
			return (Serializable) o;
		} catch (IOException exception) {
			throw exception;
		} catch (ClassNotFoundException exception) {
			throw exception;
		}
	}

	/**
	 * Serialise/binarise le tableau d'espaces libres passé en paramètre pour retourner un tableau binaire, mais selon le schéma suivant :
	 * Pour chaque interval ;
	 * <ul>
	 *     <li>écrire en binaire la position de l'interval</li>
	 *     <li>écrire en binaire la taille de l'interval</li>
	 * </ul>
	 * Utilisation pour cela la classe {@link DataOutputStream}
	 *
	 * @param freeSpaceIntervals le tableau d'espaces libres
	 * @return un tableau binaire
	 * @throws IOException si un problème d'entrée/sortie se produit
	 */
	static byte[] serializeFreeSpaceIntervals(TreeSet<BDD.FreeSpaceInterval> freeSpaceIntervals) throws IOException {
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);

			for (BDD.FreeSpaceInterval value : freeSpaceIntervals){
				dos.writeLong(value.getStartPosition());
				dos.writeLong(value.getLength());
			}
			byte[] tab = bos.toByteArray();
			return tab;
		} catch (IOException exception){
			throw exception;
		}
	}

	/**
	 * Effectue l'opération inverse de la fonction {@link #serializeFreeSpaceIntervals(TreeSet)}
	 * @param data le tableau binaire
	 * @return le tableau d'espaces libres
	 * @throws IOException si un problème d'entrée/sortie se produit
	 */
	static TreeSet<BDD.FreeSpaceInterval> deserializeFreeSpaceIntervals(byte[] data) throws IOException {
		try{
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			DataInputStream dis = new DataInputStream(bis);
			ObjectInput in = new ObjectInputStream(bis);
			//Object o = in.readObject();
		} catch (IOException exception) {
			throw exception;
		}
		return null;
	}
}
