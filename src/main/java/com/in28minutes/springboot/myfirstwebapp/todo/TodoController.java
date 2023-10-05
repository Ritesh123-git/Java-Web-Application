package com.in28minutes.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

//@Controller
//@SessionAttributes("name")

public class TodoController {
	
	private TodoService todoService;
	
	
	
	
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username= getLoggedInUsername(model);
		List<Todo> todos = todoService.findByUsername(username);
		model.put("todos",todos);
		return "listTodos";
	}


	private String getLoggedInUsername(ModelMap model) {
		return (String)model.get("name");
	}
	
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String ShowNewTodoPage(ModelMap model) {
		
		String username=getLoggedInUsername(model);

		
		Todo todo= new Todo(0,username,"",LocalDate.now().plusYears(1),false);
model.put("todo", todo);
		
		
		return "todo";
	}
	
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewTodoPage(@Valid Todo todo, ModelMap model, BindingResult result) {
		
		
		
		if(result.hasErrors()) {
			return "todo";
		}
		
		String username=getLoggedInUsername(model);
		
		
		todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), false);
		return "redirect:list-todos";
	}
	
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		//Delete todo
		
		todoService.deleteById(id);
		return "redirect:list-todos";
		
	}
	
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String ShowUpdateTodoPage(@RequestParam int id, ModelMap model) {
		//Delete todo
		Todo todo= todoService.findById(id);
		model.addAttribute("todo",todo);
		return "todo";
		
	}
	
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String UpdateTodo(@RequestParam int id,Todo todo, ModelMap model) {
		//Delete todo
		
		
		String username=getLoggedInUsername(model);
		todoService.updateTodo(todo);
		todo.setUsername(username);
		return "redirect:list-todos";
		
	}
	
	
	
	




	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}
	
	

}
