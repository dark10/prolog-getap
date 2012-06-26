package org.ldv.sio.getap.web;

import javax.servlet.http.HttpServletRequest;

import org.ldv.sio.getap.app.AccPersonalise;
import org.ldv.sio.getap.app.DemandeConsoTempsAccPers;
import org.ldv.sio.getap.app.FormListConsoForProfInter;
import org.ldv.sio.getap.app.User;
import org.ldv.sio.getap.app.service.IFManagerGeTAP;
import org.ldv.sio.getap.utils.UtilSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Web controller for hotel related actions.
 */
@Controller
@RequestMapping("/prof-intervenant/*")
public class ProfInterController {

	@Autowired
	@Qualifier("DBServiceMangager")
	private IFManagerGeTAP manager;

	public void setManagerEleve(IFManagerGeTAP serviceManager) {
		this.manager = serviceManager;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String editDCTAPById(@RequestParam("id") String id,
			FormListConsoForProfInter dctap, Model model) {

		System.out.println("TEST id recu :" + dctap.getId());

		model.addAttribute("lesAP", manager.getAllAPForProf());

		DemandeConsoTempsAccPers currentDctap = manager.getDCTAPById(Long
				.valueOf(id));
		if (currentDctap.getEtat() == 0 || currentDctap.getEtat() == 4
				|| currentDctap.getEtat() > 1023) {
			// valorise le bean de vue avec le dctap courant
			dctap.setId(currentDctap.getId()); // en provenance d'un champ caché
			dctap.setDateAction(currentDctap.getDateAction());
			dctap.setMinutes(currentDctap.getMinutes());
			model.addAttribute("minute", currentDctap.getMinutes());
			dctap.setAccPersId(currentDctap.getAccPers().getId());

			return "prof-intervenant/edit";
		}
		return "prof-intervenant/index";
	}

	/**
	 * Default action, displays the use case page.
	 * 
	 * 
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public void index(Model model) {
		User me = UtilSession.getUserInSession();
		model.addAttribute("listdctaps", manager.getAllDCTAPByProfInterv(me));
		Long id = me.getId();
		model.addAttribute("etat0", manager.getAllDCTAPByEtat(0, id));
		model.addAttribute("etat1", manager.getAllDCTAPByEtat(1, id));
		model.addAttribute("etat2", manager.getAllDCTAPByEtat(2, id));
		model.addAttribute("etat4", manager.getAllDCTAPByEtat(4, id));

		model.addAttribute("etat16", manager.getAllDCTAPByEtat(16, id));
		model.addAttribute("etat32", manager.getAllDCTAPByEtat(32, id));
		model.addAttribute("etat64", manager.getAllDCTAPByEtat(64, id));
		model.addAttribute("etatsup1000", manager.getAllDCTAPModifByEtat(id));
	}

	@RequestMapping(value = "doedit", method = RequestMethod.POST)
	public String doeditDCTAPById(FormListConsoForProfInter formDctap,
			BindingResult bindResult, Model model) {
		System.out.println("TEST :" + formDctap.getId());
		System.out.println("TEST :" + model);

		if (bindResult.hasErrors())
			return "prof-intervenant/edit";
		else {

			DemandeConsoTempsAccPers dctapForUpdate = manager.getDCTAPById(Long
					.valueOf(formDctap.getId()));

			AccPersonalise acc = manager.getAPById(formDctap.getAccPersId());
			String accPersNom = acc.getNom();

			if (!dctapForUpdate.getDateAction().equals(
					formDctap.getDateAction())
					&& !dctapForUpdate.isDateModifiee()) {
				dctapForUpdate.setEtat(dctapForUpdate.getEtat() + 1024);
			}
			if (!dctapForUpdate.getMinutes().equals(formDctap.getMinutes())
					&& !dctapForUpdate.isDureeModifiee()) {
				dctapForUpdate.setEtat(dctapForUpdate.getEtat() + 2048);
			}
			if (!dctapForUpdate.getAccPers().getNom().equals(accPersNom)
					&& !dctapForUpdate.isApModifiee()) {
				dctapForUpdate.setEtat(dctapForUpdate.getEtat() + 4096);
			}

			dctapForUpdate.setDateAction(formDctap.getDateAction());
			dctapForUpdate.setMinutes(formDctap.getMinutes());
			dctapForUpdate.setAccPers(manager.getAPById(formDctap
					.getAccPersId()));

			manager.updateDCTAP(dctapForUpdate);

			return "redirect:/app/prof-intervenant/index";
		}
	}

	@RequestMapping(value = "refuse/{id}", method = RequestMethod.GET)
	public String refuseDCTAPById(@PathVariable String id, Model model) {
		DemandeConsoTempsAccPers dctap = manager.getDCTAPById(Long.valueOf(id));

		// Test que la DCTAP appartient à la bonne personne
		if (dctap.getProf().equals(UtilSession.getUserInSession())
				&& (dctap.getEtat() == 0 || dctap.getEtat() == 4 || dctap
						.getEtat() > 1023)) {
			dctap.setEtat(64);
			manager.updateDCTAP(dctap);
		}

		return "redirect:/app/prof-intervenant/index";
	}

	@RequestMapping(value = "valid/{id}", method = RequestMethod.GET)
	public String validDCTAPById(@PathVariable String id, Model model) {
		DemandeConsoTempsAccPers dctap = manager.getDCTAPById(Long.valueOf(id));

		// Test que la DCTAP appartient à la bonne personne
		if (dctap.getProf().equals(UtilSession.getUserInSession())
				&& (dctap.getEtat() == 0 || dctap.getEtat() == 4)) {
			dctap.setEtat(32);
			manager.updateDCTAP(dctap);
		}

		return "redirect:/app/prof-intervenant/index";
	}

	@RequestMapping(value = "sendId", method = RequestMethod.POST)
	public String listIdDctap(Model model, HttpServletRequest request) {

		// TODO recupérer le tableau d'id dans la classe FormListIdDctap au lieu
		// du request
		String[] listId = request.getParameterValues("ids");
		if (request.getParameter("send").equals("Valider")) {
			try {
				for (int i = 0; i < listId.length; i++) {
					DemandeConsoTempsAccPers dctap = manager.getDCTAPById(Long
							.valueOf(listId[i]));

					// Test que la DCTAP appartient à la bonne personne
					if (dctap.getProf().equals(UtilSession.getUserInSession())
							&& (dctap.getEtat() == 0 || dctap.getEtat() == 4)) {
						dctap.setEtat(32);
						manager.updateDCTAP(dctap);
					}
				}
			} catch (NullPointerException e) {

			}
		} else {
			try {
				for (int i = 0; i < listId.length; i++) {
					DemandeConsoTempsAccPers dctap = manager.getDCTAPById(Long
							.valueOf(listId[i]));

					// Test que la DCTAP appartient à la bonne personne
					if (dctap.getProf().equals(UtilSession.getUserInSession())
							&& (dctap.getEtat() == 0 || dctap.getEtat() == 4 || dctap
									.getEtat() > 1023)) {
						dctap.setEtat(64);
						manager.updateDCTAP(dctap);
					}
				}
			} catch (NullPointerException e) {

			}
		}

		return "redirect:/app/prof-intervenant/index";
	}
}
