package br.com.alura.linguagens.api;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinguagemService {

	final LinguagemRepository linguagemRepository;

	public LinguagemService(LinguagemRepository linguagemRepository) {
		this.linguagemRepository = linguagemRepository;
	}

	@Transactional
	public Linguagem save(Linguagem linguagem) {
		return linguagemRepository.save(linguagem);
	}

	public Optional<Linguagem> findById(String id) {
		return linguagemRepository.findById(id);
	}

	@Transactional
	public void delete(Linguagem linguagem) {
		linguagemRepository.delete(linguagem);
	}

}
