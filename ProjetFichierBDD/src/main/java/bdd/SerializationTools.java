package bdd;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.TreeSet;

/**
 * Classe qui contient des outils de sérialization
 *
 * @author Jason Mahdjoub
 * @version 1.0
 */
class SerializationTools {
	public static long convertToLong(byte[] array) {
		ByteBuffer buffer = ByteBuffer.wrap(array);
		return buffer.getLong();
	}
	/**
	 * Serialise/binarise l'objet passé en paramètre pour retourner un tableau binaire
	 * @param o l'objet à serialiser
	 * @return the tableau binaire
	 * @throws IOException si un problème d'entrée/sortie se produit
	 */
	static byte[] serialize(Serializable o) throws IOException {
		/*ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;*/
		try {
			if(o != null){
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutput output = new ObjectOutputStream(bos);
				output.writeObject(o);
				return bos.toByteArray();
			} else {
				throw new NullPointerException();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
			throw exception;
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
		if(data != null){
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInput in = new ObjectInputStream(bis);
			return (Serializable) in.readObject();
		} else {
			throw new NullPointerException();
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
		try {
			if (freeSpaceIntervals != null){
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				for (BDD.FreeSpaceInterval value : freeSpaceIntervals) {
					dos.writeLong(value.getStartPosition());
					dos.writeLong(value.getLength());
				}
				byte[] tab = bos.toByteArray();
				return tab;
			} else {
				throw new NullPointerException();
			}
		}
		catch (IOException e){
			throw e;
		}
	}

	/**
	 * Effectue l'opération inverse de la fonction {@link #serializeFreeSpaceIntervals(TreeSet)}
	 * @param data le tableau binaire
	 * @return le tableau d'espaces libres
	 * @throws IOException si un problème d'entrée/sortie se produit
	 */
	static TreeSet<BDD.FreeSpaceInterval> deserializeFreeSpaceIntervals(byte[] data) throws IOException {
		if(data != null){
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			TreeSet<BDD.FreeSpaceInterval> tree = new TreeSet<BDD.FreeSpaceInterval>();
			byte[] tab = new byte[8];
			byte[] tab2 = new byte[8];
			while(bis.read(tab)!=-1&&bis.read(tab2)!=-1)
			{
				tree.add(new BDD.FreeSpaceInterval(convertToLong(tab),convertToLong(tab2)));
			}
			return tree;
		} else {
			throw new NullPointerException();
		}
	}
}
