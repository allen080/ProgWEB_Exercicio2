package com.example.demo;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/")
public class TurmaController {
	@Autowired
	TurmaRepository turmaRep;
	@Autowired
	ProfessorRepository profRep;
	@Autowired
	AlunoRepository alunoRep;

	@GetMapping("/turmas")
	public List<Turma> getAllTurmas(){
		return turmaRep.findAll();
	}
	
	@GetMapping("/turmas/{id}")
	public Turma getAluno(@PathVariable Long id){
		return turmaRep.findById(id).get();
	}
	
	@PostMapping("/turmas")
	public Turma saveTurma(@RequestBody Turma turma){
		return turmaRep.save(turma);
	}
	
	@PutMapping("/turmas/{id}")
	public ResponseEntity<Turma> updateTurma(@PathVariable Long id, @RequestBody Turma turma){
		Turma novaTurma = turmaRep.findById(id).get();
		
		if(turma.getNome() != null)
			novaTurma.setNome(turma.getNome());
		if(turma.getSala() != null)
			novaTurma.setSala(turma.getSala()); 
		
		final Turma turmaAtt = turmaRep.save(novaTurma);
		return ResponseEntity.ok(turmaAtt);
	}
	
	@DeleteMapping("/turmas/{id}")
	public void deleteTurma(@PathVariable Long id){
		turmaRep.deleteById(id);
	}
	
	@PatchMapping("/turmas/{turmaId}/vincularProf/{profId}")
	public void addProfOnTurma(@PathVariable Long turmaId, @PathVariable Long profId) {
		System.out.printf("prof: %d\nturma: %d\n", profId, turmaId);
	}
	
	@GetMapping("/zuzu")
	public void soTeste(){
		try {
			System.out.println(alunoRep.getReferenceById(1l));
		} catch(EntityNotFoundException e) {
			System.out.println("nop");
		}
	}
	
	@PatchMapping("/turmas/{turmaId}/matricularAluno/{alunoId}")
	public String matricularAlunoEmTurma(@PathVariable Long turmaId, @PathVariable Long alunoId, @RequestBody Professor prof) {
		//ProfessorRepository profRep = new ProfessorRepository();
		List<Professor> professores = profRep.findAll();
		List<Aluno> alunos = alunoRep.findAll();

		boolean matriculado = false;
		boolean temEmail = prof.getEmail() != null;
		boolean temMatricula = prof.getEmail() != null;
		
		
		if(!temEmail)
			return "[!] Email do professor nao encontrada";
		else if(!temMatricula)
			return "[!] Matricula do professor nao encontrada";
		
		for(int i=0; i<professores.size(); i++){
			Professor pAtual = professores.get(i);
			
			if(pAtual.getEmail() == prof.getEmail() && pAtual.getMatricula() == prof.getMatricula()){
				System.out.println(alunoRep.getReferenceById(alunoId));
				matriculado = true;
				break;
			}
		}
		return "[*] Matriculado com sucesso";
	}
}	
