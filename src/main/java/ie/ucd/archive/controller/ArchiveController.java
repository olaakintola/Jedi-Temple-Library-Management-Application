package ie.ucd.archive.controller;

import ie.ucd.archive.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class ArchiveController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    //int media_count = (int)mediaRepository.count();
    // int user_count = (int)userRepository.count();
    // int trans_count = (int)transactionRepository.count();
    int media_count;
    int user_count;
    int trans_count;

    public ArchiveController(UserSession userSession, UserRepository userRepository, MediaRepository mediaRepository, TransactionRepository transactionRepository) {
        this.userSession = userSession;
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
        this.transactionRepository = transactionRepository;
        this.media_count = 0;
        this.user_count = 0;
        this.trans_count = 0;

        /*
        MediaReader excelReader = new MediaReader();
        excelReader.readXLSX(20, "src/main/resources/static/spreadsheets/books.xlsx");
        ArrayList<Media> media = excelReader.getMedia();
        for (Media medium :
                media) {
            mediaRepository.save(medium);
            ++media_count;
        }
        */
    }

    @GetMapping("/view")
    public String view(Integer id, Model model) {
        setUpNavBar(model);
        ArrayList<Media> artifacts = new ArrayList<>(mediaRepository.findAll());
        model.addAttribute(artifacts.get(id - 1));
        //System.out.println("artifact loaded" + artifacts);
        return "view.html";
    }

    @GetMapping("/create")
    public String create(Model model) {
        setUpNavBar(model);
        return "create.html";
    }

    @PostMapping("/create")
    public void create(String title, String mediatext, String mediatype, Boolean is_child_suitable, HttpServletResponse response) throws IOException {
        //System.out.println(media_count);
        //System.out.println(mediaRepository.count());
        Media new_media = new Media();
        new_media.setTitle(title);
        new_media.setMediatext(mediatext);
        new_media.setMediatype(mediatype);
        new_media.setIs_child_suitable(is_child_suitable);
        mediaRepository.save(new_media);
        media_count++;
        //System.out.println("Media CREATED: " + new_media + "\t Message ID: " + new_media.getId());
        response.sendRedirect("/catalogue");
    }

    @GetMapping("/user")
    public String user(Model model) {
    	setUpNavBar(model); 
        if(userSession.getUser() != null) {
            if (!userSession.getLoginFailed()) {
                model.addAttribute(userSession.getUser());
                //System.out.println("USER DETAILS LOADED" + userSession.getUser().user_details());
                return "user.html";
            }
            return "signup.html";
        }
        return "signup.html";
    }


    @GetMapping("/history")
    public String history(Model model) {
        setUpNavBar(model);
        //System.out.println("In /history before if:");
        if (!userSession.getLoginFailed()) {
            //System.out.println("------------------USER TRANSACTIONS LOADED------------------");
            //System.out.println(userSession.getUser().user_details());
            List<Transaction> total = transactionRepository.findAllByUserAndActiveEquals(userSession.getUser(), true);
            List<Transaction> inactive = transactionRepository.findAllByUserAndActiveEquals(userSession.getUser(), false);
            total.addAll(inactive);
            model.addAttribute("transactions", total);
        }
        //System.out.println("In /history after if:");
        return "history.html";
    }


    @GetMapping("/")
    public String index(Model model) {
        setUpNavBar(model);
        return "index.html";
    }

    @GetMapping("/template")
    public String template(Model model) {
        setUpNavBar(model);
        return "template.html";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        setUpNavBar(model);
        return "signup.html";
    }

    @GetMapping("/users")
    public String users(Model model) {
        setUpNavBar(model);
        List<User> results = userRepository.findAll();
        //System.out.println(results.size());
        model.addAttribute("users", results);
        return "users.html";
    }

    //public void reserveProcess(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {

    @GetMapping("/update")
    public String update(Model model) {
        setUpNavBar(model);
        return "update.html";
    }

    @GetMapping("/error")
    public String error(Model model) {
        setUpNavBar(model);
        return "error.html";
    }

    @PostMapping("/update")
    public void process(Integer id, String title, String mediatext, String mediatype, String
            createType, HttpServletResponse response) throws IOException {
        //System.out.println(media_count);
        //System.out.println(mediaRepository.count());
        //System.out.println(id);
        Optional<Media> update_media = mediaRepository.findById(id);
        if (update_media.isPresent()) {
            //System.out.println(id + ": already exits , updating media object");
            if(title != null)  {
                if(!title.equals("")) {
                    update_media.get().setTitle(title);
                }
            }
            if(mediatext != null) {
                if(!mediatext.equals("")) {
                    update_media.get().setMediatext(mediatext);
                }
            }
            if(mediatype != null) {
                if(!mediatype.equals("")) {
                    update_media.get().setMediatype(mediatype);
                }
            }
            if(createType != null) {
                update_media.get().setIs_child_suitable(createType.equals("CHILD"));
            }
            mediaRepository.save(update_media.get());
            //System.out.println("Media UPDATED: " + update_media.get() + "\t Message ID: " + update_media.get().getId() + update_media.get().getIs_child_suitable());
        } /*else {
            System.out.println("MEDIA:" + id + "DOES NOT EXIST");
        }*/
        response.sendRedirect("/catalogue");
    }

    private void setUpNavBar(Model model) {
        //System.out.println("Beginning of setUpNavBar");
        String presentUserRole;
        if (userSession.getUser() == null) presentUserRole = "noUser";
        else presentUserRole = userSession.getUser().getRole();
        model.addAttribute("userRole", presentUserRole);
        //System.out.println("setting up navigation bar:" + presentUserRole);
    }


    private void prepareCatalogueMedia(Model model, List<Media> mediaList) {
        //System.out.println("In prepareCatalogueMedia");
        List<Media> usersLoans = new ArrayList<>(0);
        if (userSession.getUser() != null) {
            for (Media item : mediaList) {
                List<Transaction> currentRelevantTransactions = transactionRepository.findAllByUserAndMediaAndTypeAndActive(userSession.getUser(), item, "LOAN", true);
                if (!currentRelevantTransactions.isEmpty()) {
                    usersLoans.add(item);
                }
            }
        }
        model.addAttribute("usersLoans", usersLoans);
        //System.out.println("usersLoans size:" + usersLoans.size());

        List<Media> usersReservations = new ArrayList<>(0);
        if (userSession.getUser() != null) {
            for (Media item : mediaList) {
                List<Transaction> currentRelevantTransactions = transactionRepository.findAllByUserAndMediaAndTypeAndActive(userSession.getUser(), item, "RESERVATION", true);
                if (!currentRelevantTransactions.isEmpty()) {
                    usersReservations.add(item);
                }
            }
        }
        model.addAttribute("usersReservations", usersReservations);
        //System.out.println("usersReservations size:" + usersReservations.size());
    }

    @GetMapping("/catalogue")
    public String catalogue(Model model) {
        setUpNavBar(model);
        List<Media> results = mediaRepository.findAllByIsviewable(true);
        results = removeInappropriateContent(results);
        model.addAttribute("media", results);
        prepareCatalogueMedia(model, results);
        return "catalogue.html";
    }

    @PostMapping("/searchedMedia")
    public String searchedMedia(String search, String showInvisible, Model model, HttpServletResponse response) {
        setUpNavBar(model);
        List<Media> results;
        if(search.equals(""))  results = mediaRepository.findAll();
        else {
            results = mediaRepository.findByTitleIgnoreCaseContaining(search);
            results.addAll(mediaRepository.findByMediatextIgnoreCaseContaining(search));
            results.addAll(mediaRepository.findByMediatypeIgnoreCaseContaining(search));
        }
        results = removeDuplicates(results);
        results = removeInappropriateContent(results);
        //System.out.println("In /searchedMedia, this is showInvisible:");
        if(showInvisible == null) {
            //System.out.println("Removing invisible media here");
            removeInvisibleMedia(results);
        }
        model.addAttribute("media", results);
        prepareCatalogueMedia(model, results);
        return "catalogue.html";
    }
    
    private List<Media> removeInappropriateContent(List<Media> list) {
        //System.out.println("remInCon");
        List<Media> output = new ArrayList<>();
        if(userSession.getUser() != null) {
            //System.out.println("user not null");
            if(userSession.getUser().getIs_child()) {
                //System.out.println("user is a child");
                for (Media item :
                        list) {
                    if (item.getIs_child_suitable()) {
                        //System.out.println("found suitable item, adding it to the list");
                        output.add(item);
                    }
                }
                //System.out.println("about to return shortened list");
                return output;
            }
        }
        //System.out.println("about to return long list");
        return list;
    }

    private void removeInvisibleMedia(List<Media> list) {
        list.removeIf(item -> !item.getIsviewable());
    }

    private List<Media> removeDuplicates(List<Media> listWithDuplicates) {
        return new ArrayList<>(new HashSet<>(listWithDuplicates));
    }


    @RequestMapping(value = "/reserve", method = {RequestMethod.GET, RequestMethod.POST})
    public void reserveProcess(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        if (userSession.getLoginFailed() == null) userSession.setLoginFailed(true);
        if (!userSession.getLoginFailed()) {
            Optional<Media> reserve_media = mediaRepository.findById(id);
            if (reserve_media.isPresent()) {
                reserve_media.get().setIs_reserved(true);
                reserve_media.get().setIs_loanable(false);
                mediaRepository.save(reserve_media.get());
                Transaction new_transaction = new Transaction(userSession.getUser(), reserve_media.get(), "RESERVATION", true);
                trans_count++;
                transactionRepository.save(new_transaction);
                Optional<User> temp_user = userRepository.findById(userSession.getUser().getId());
                //System.out.println(userSession.getUser().user_details());
                temp_user.ifPresent(user -> userSession.setUser(user));
                response.sendRedirect("/catalogue");
            }
        } else {
            //System.out.println("ONLY MEMBERS CAN LOAN/RESERVE FROM LIBRARY PLEASE CREATE AN ACCOUNT");
            response.sendRedirect("/");
        }
    }

    // occurs from catalogue, when admin deletes Media
    @RequestMapping(value = "/adminLoanOut", method = {RequestMethod.GET, RequestMethod.POST})
    public void adminLoanOut(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        if (!userSession.getLoginFailed()) {
            if (userSession.getUser().getRole().equals("ADMIN")) {
                Optional<Media> media_obj = mediaRepository.findById(id);
                if (media_obj.isPresent()) {
                    //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIs_loanable());
                    media_obj.get().setIs_loanable(false);
                    mediaRepository.save(media_obj.get());
                    //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIs_loanable());

                } /*else {
                    System.out.println("NO ID REDIRECTING");
                }*/
                response.sendRedirect("/catalogue");
            }
        } else {
            //System.out.println("NO VALID USER REDIRECTING");
            response.sendRedirect("/catalogue");
        }
    }

    // occurs from catalogue, when admin deletes Media
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public void delete(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        if (!userSession.getLoginFailed()) {
            if (userSession.getUser().getRole().equals("ADMIN")) {
                Optional<Media> media_obj = mediaRepository.findById(id);
                if (media_obj.isPresent()) {
                    //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIsviewable());
                    media_obj.get().setIsviewable(false);
                    mediaRepository.save(media_obj.get());
                    //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIsviewable());

                } /*else {
                    System.out.println("NO ID REDIRECTING");
                }*/
                response.sendRedirect("/catalogue");
            }
        } else {
            //System.out.println("NO VALID USER REDIRECTING");
            response.sendRedirect("/catalogue");
        }
    }

    // occurs from catalogue, when admin restores Media
    @RequestMapping(value = "/restore", method = {RequestMethod.GET, RequestMethod.POST})
    public void restores(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        if (!userSession.getLoginFailed()) {
            if (userSession.getUser().getRole().equals("ADMIN")) {
                Optional<Media> media_obj = mediaRepository.findById(id);
                if (media_obj.isPresent()) {
                    //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIsviewable());
                    media_obj.get().setIsviewable(true);
                    mediaRepository.save(media_obj.get());
                    //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIsviewable());

                } /*else {
                    System.out.println("NO ID REDIRECTING");
                }*/
                response.sendRedirect("/catalogue");
            }
        } else {
            //System.out.println("NO VALID USER REDIRECTING");
            response.sendRedirect("/catalogue");
        }
    }

    // occurs from catalogue, when admin updates a media id
    @PostMapping("/preformAdminAction")
    public void preformAdminAction(Integer id, String action, HttpServletResponse response) throws IOException {
        if (!userSession.getLoginFailed()) {
            if (userSession.getUser().getRole().equals("ADMIN")) {
                Optional<Media> media_obj = mediaRepository.findById(id);
                if (media_obj.isPresent()) {
                    //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIsviewable());
                    switch (action) {
                        case "delete":
                            //System.out.println("ACTION:" + action);
                            media_obj.get().setIsviewable(false);
                            mediaRepository.save(media_obj.get());
                            //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIsviewable());
                            response.sendRedirect("/catalogue");
                            break;
                        case "add":
                            //System.out.println("ACTION:" + action);
                            media_obj.get().setIsviewable(true);
                            mediaRepository.save(media_obj.get());
                            //System.out.println(media_obj.get().getId() + " STATUS :" + media_obj.get().getIsviewable());
                            response.sendRedirect("/catalogue");
                            break;
                    }
                } else {
                    //System.out.println("NO ID REDIRECTING");
                    response.sendRedirect("/catalogue");
                }
            }
        } else {
            //System.out.println("NO VALID USER REDIRECTING");
            response.sendRedirect("/catalogue");
        }

    }

    @RequestMapping(value = "/return", method = {RequestMethod.GET, RequestMethod.POST})
    public void returnMedia(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        if (userSession.getLoginFailed() == null) userSession.setLoginFailed(true);
        if (!userSession.getLoginFailed()) {
            Optional<Media> return_media = mediaRepository.findById(id);
            if (return_media.isPresent()) {
                //System.out.println("Media name and id:" + return_media.get().getTitle() + "_" + return_media.get().getId());
                Optional<Transaction> oldTransaction = transactionRepository.findByMediaAndTypeAndActive(return_media.get(), "LOAN", true);
                if (oldTransaction.isPresent()) {
                    oldTransaction.get().setActive(false);
                    transactionRepository.save(oldTransaction.get());
                    //System.out.println("oldTransaction :" + oldTransaction.get().transaction_details());
                }
                if (return_media.get().getIs_reserved()) {
                    List<Transaction> allReservations = transactionRepository.findAllByMediaAndTypeAndActive(return_media.get(), "RESERVATION", true);
                    if (findFirstTransaction(allReservations).isPresent()) {
                        Transaction nextReservation = findFirstTransaction(allReservations).get();
                        nextReservation.setActive(false);
                        //System.out.println("The oldest reservation is present:" + nextReservation.transaction_details());
                        transactionRepository.save(nextReservation);
                        return_media.get().setIs_loanable(false);
                        //System.out.println("This is the number of reservations on this object:" + allReservations.size());
                        if (allReservations.size() == 1) return_media.get().setIs_reserved(false);
                        Transaction new_transaction = new Transaction();
                        new_transaction.setUser(nextReservation.getUser());
                        new_transaction.setMedia(return_media.get());
                        new_transaction.setType("LOAN");
                        new_transaction.setActive(true);
                        //System.out.println("Newest loan transaction will be:" + new_transaction.transaction_details());
                        transactionRepository.save(new_transaction);
                        User newLoaner = new_transaction.getUser();
                        newLoaner.getAll_transactions().add(new_transaction);
                        //System.out.println("this users state:" + newLoaner.user_details());
                        userRepository.save(newLoaner);
                    }
                } else {
                    return_media.get().setIs_loanable(true);
                    //System.out.println("Returned media wasn't reserved:" + return_media.get().media_details());
                    mediaRepository.save(return_media.get());
                }
            }
        }
        //System.out.println("End of /return");
        response.sendRedirect("/catalogue");
    }

    @RequestMapping(value = "/renew", method = {RequestMethod.GET, RequestMethod.POST})
    public void renewMedia(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        if (userSession.getLoginFailed() == null) userSession.setLoginFailed(true);
        if (!userSession.getLoginFailed()) {
            Optional<Media> renew_media = mediaRepository.findById(id);
            if (renew_media.isPresent()) {
                //System.out.println("renew_media is present and this is its id");
                //System.out.println(renew_media.get().getId());
                if (!renew_media.get().getIs_reserved()) {
                    //System.out.println("renew_media is not reserved, can be renewed");
                    renew_media.get().setIs_loanable(false);
                    mediaRepository.save(renew_media.get());
                    Optional<Transaction> oldTransaction = transactionRepository.findByUserAndMediaAndTypeAndActive(userSession.getUser(), renew_media.get(), "LOAN", true);
                    if (oldTransaction.isPresent()) {
                        transactionRepository.save(oldTransaction.get());
                        Optional<User> temp_user = userRepository.findById(userSession.getUser().getId());
                        //System.out.println(userSession.getUser().user_details());
                        temp_user.ifPresent(user -> userSession.setUser(user));
                    }
                }
            }
            response.sendRedirect("/catalogue");
        }
    }

    @RequestMapping(value = "/loan", method = {RequestMethod.GET, RequestMethod.POST})
    public void loanMedia(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        if (userSession.getLoginFailed() == null) userSession.setLoginFailed(true);
        if (!userSession.getLoginFailed()) {
            Optional<Media> loan_media = mediaRepository.findById(id);
            if (loan_media.isPresent()) {
                //System.out.println("loan_media is present and this is its id");
                //System.out.println(loan_media.get().getId());
                if (loan_media.get().getIs_loanable()) {
                    //System.out.println("loan_media is loanable");
                    loan_media.get().setIs_loanable(false);
                    mediaRepository.save(loan_media.get());
                    Transaction new_transaction = new Transaction();
                    new_transaction.setUser(userSession.getUser());
                    //new_transaction.setReturned_date(new_transaction.addLoanDuration(new_transaction.getCreated_date().getTime()));
                    new_transaction.setMedia(loan_media.get());
                    new_transaction.setType("LOAN");
                    new_transaction.setActive(true);
                    trans_count++;
                    transactionRepository.save(new_transaction);
                    Optional<User> temp_user = userRepository.findById(userSession.getUser().getId());
                    //System.out.println(userSession.getUser().user_details());
                    //System.out.println(new_transaction.transaction_details());
                    temp_user.ifPresent(user -> userSession.setUser(user));
                } /*else {
                    System.out.println("loan_media is not loanable, not loaning out");
                }*/
            }
        } else {
            //System.out.println("ONLY MEMBERS CAN LOAN FROM LIBRARY PLEASE CREATE AN ACCOUNT");
            response.sendRedirect("/");
        }
        response.sendRedirect("/catalogue");
    }

    private Optional<Transaction> findFirstTransaction(List<Transaction> transactionList) {
        long min = 1000000;
        for (Transaction transaction : transactionList) {
            if (transaction.getId() < min) min = transaction.getId();
        }
        //System.out.println("findFirstTransaction, min trans id:" + min);
        return transactionRepository.findById(min);
    }
}