package ikab.dev.planner.service;

import ikab.dev.planner.clients.TopoClient;
import ikab.dev.planner.models.Eoloplant;
import ikab.dev.planner.models.EoloplantCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PlannerService {

	@Autowired
	private TopoClient topoClient;

	public void createNewEoloplant(EoloplantCreationRequest request) {

		Eoloplant eoloplant = new Eoloplant(request.getId(), request.getCity());

		CompletableFuture<String> landscape = topoClient.getLandscape(request.getCity());

		CompletableFuture<Void> allFutures = CompletableFuture.allOf(landscape);

		eoloplant.advanceProgress();

		landscape.thenAccept(eoloplant::addPlanning);

		allFutures.thenRun(() -> {
			simulateProcessWaiting();
			eoloplant.processPlanning();
		});
	}

	private void simulateProcessWaiting() {
		try {
			Thread.sleep((long) (Math.random() * 2000 + 1000));
		} catch (InterruptedException ignored) {}
	}
}
