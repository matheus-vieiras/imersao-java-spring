package br.com.alura.linguagens.api;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/linguagens")
public class LinguagemController {

	@Autowired
	private LinguagemRepository repositorio;

	@GetMapping
	public List<Linguagem> obterLinguagens() {
		List<Linguagem> linguagens = repositorio.findAll();

		ToIntFunction<Linguagem> ordenarPorRanking = rank -> rank.getRanking();
		Comparator<Linguagem> compararRanking = Comparator.comparingInt(ordenarPorRanking);
		linguagens.sort(compararRanking);

		return linguagens;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable(value = "id") String id) {
		Optional<Linguagem> linguagemOptional = repositorio.findById(id);
		if (!linguagemOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Linguagem não encontrada.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(linguagemOptional.get());
	}

	@PostMapping
	public Linguagem cadastrarLinguagem(@RequestBody Linguagem linguagem) {
		Linguagem linguagemSalva = repositorio.save(linguagem);
		return linguagemSalva;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteLinguagem(@PathVariable(value = "id") String id) {
		Optional<Linguagem> linguagemOptional = repositorio.findById(id);
		if (!linguagemOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Linguagem não encontrada.");
		}
		repositorio.delete(linguagemOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Linguagem deletada com sucesso!");
	}

	@PutMapping("/{id}")

	public Linguagem atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem) {
		Linguagem linguagemAtualizada = repositorio.findById(id).get();
		BeanUtils.copyProperties(linguagem, linguagemAtualizada, "id");
		return repositorio.save(linguagemAtualizada);

	}
}
