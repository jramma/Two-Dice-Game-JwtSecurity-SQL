package cat.juego.dados.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.juego.dados.model.domain.Partida;
import cat.juego.dados.model.domain.Usuario;
import cat.juego.dados.model.repository.PartidaRepository;
import cat.juego.dados.model.repository.UserRepository;

@Service
public class ImplementsUService implements UserService {

	@Autowired
	private PartidaRepository partidas;

	@Autowired
	private UserRepository usuarios;

	@Override
	public Usuario saveUser(Usuario usuario) {
		// String nombre = buscarUsuario(usuario);
		return usuarios.save(usuario);

	}

	@Override
	public Partida jugar(Usuario usuario) {
		return new Partida(usuario.getNombre(), (int) (Math.random() * 6), (int) (Math.random() * 6));
	}

	@Override
	public void guardarPartida(Partida partida, Usuario usuario) {
		usuario.getPartidas().add(partida);
		partidas.save(partida);
	}

	@Override
	public void deletePartidasUser(Integer id) {

		usuarios.deleteById(id);
	}

	@Override
	public List<Usuario> jugadores() {
		return usuarios.findAll();
	}

	@Override
	public List<Partida> listaJugadas(Usuario usuario) {
		return usuario.getPartidas();
	}

	@Override
	public double ranquingTotal() {
		ArrayList<Integer> victorias = null;
		ArrayList<Integer> derrotas = null;
		double media = 0;
		for (int i = 0; i < usuarios.findAll().size(); i++) {
			for (int j = 0; j < usuarios.findAll().get(i).getPartidas().size(); j++) {
				if (usuarios.findAll().get(i).getPartidas().get(j).getResultado().equalsIgnoreCase("victory")) {
					victorias.add(1);
				} else {
					derrotas.add(1);
				}
			}
		}

		media = victorias.size() / victorias.size() + derrotas.size();
		return media;

	}

	@Override
	public Usuario peorUsuario() {
		List<Usuario> usuarios1 = usuarios.findAll();
		Usuario looser = null;
		for (int i = 0; i < usuarios1.size() - 1; i++) {
			if (usuarios1.get(i).getRanquing() < usuarios1.get(i + 1).getRanquing()) {
				looser = usuarios1.get(i);
			}
		}

		return looser;
	}

	@Override
	public Usuario mejorUsuario() {
		List<Usuario> usuarios1 = usuarios.findAll();
		Usuario winner = null;
		for (int i = 0; i < usuarios1.size() - 1; i++) {
			if (usuarios1.get(i).getRanquing() < usuarios1.get(i + 1).getRanquing()) {
				winner = usuarios1.get(i);
			}
		}

		return winner;
	}

	@Override
	public List<Partida> listaJugadas() {
		return partidas.findAll();

	}

	@SuppressWarnings("deprecation")
	@Override
	public Usuario findById(int id) {
		return usuarios.getById(id);
	}

	@Override
	public int victoriasTotales() {
		int victorias = 0;

		for (int i = 0; i < partidas.findAll().size(); i++) {
			if (partidas.getById(i).getResultado().equals("victory")) {
				victorias++;
			}
		}

		return victorias;
	}
}
