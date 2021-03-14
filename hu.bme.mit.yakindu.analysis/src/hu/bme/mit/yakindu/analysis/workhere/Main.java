package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.Scope;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() throws IOException {
		main(new String[0]);
	}

	public static void main(String[] args) throws IOException {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();

		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");

		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		//System.out.println("public static void print(IExampleStatemachine s){");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof Scope) {
				Scope scope = (Scope) content;
				for(Event event: scope.getEvents()) {
					System.out.println("BufferedReader br = new BufferedReader(new InputStreamReader(System.in));");
					System.out.println("String eventName = br.readLine();");
					System.out.println("System.out.println(eventName);");
					for(Property variable: scope.getVariables()) {
						System.out.println("System.out.println(" + variable.getName().toUpperCase().charAt(0)
								+ " = s.getSCInterface().get" + variable.getName().toUpperCase().charAt(0) + variable.getName().substring(1) + "());");
					}
					System.out.println("switch (eventName) {");
					System.out.println("  case " + event.getName() + ":");
					System.out.println("    sm.raise" + event.getName().toUpperCase().charAt(0) + event.getName().substring(1) + "();");
					System.out.println("    break;");
				}

			}
		}
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}