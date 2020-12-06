
import java.util.Iterator;

public class CrazyMarket implements MyQueue<Customer> {

    Node head;
    int numberOfElements = 0;

    /**
     * default tekerleme
     */
    String tekerleme = "O piti piti karamela sepeti " + "\nTerazi lastik jimnastik "
            + "\nBiz size geldik bitlendik Hamama gittik temizlendik.";

    /**
     * numberOfCustumer ile verilen sayida musteri hizmet gorene kadar calismaya
     * devam eder
     */
    public CrazyMarket(int numberOfCustomer) {
        this._crazyMarket(numberOfCustomer);
    }

    /**
     * numberOfCustumer ile verilen sayida musteri hizmet gorene kadar calismaya
     * devam eder, calisirken verilen tekerlemeyi kullanir
     */
    public CrazyMarket(int numberOfCustomer, String tekerleme) {
        System.out.println("CRAZY MARKET");
        this.tekerleme = tekerleme;
        this._crazyMarket(numberOfCustomer);
    }

    private void _crazyMarket(int numberOfCustomer) { // 10

        Customer chosenCustomer = null;

        for (int i = 0; i < numberOfCustomer; i++) {
            System.out.println("---------------------------Yeni Tur-------------------------");

            Customer newCustomer = new Customer(i + 1);

            // Kasada Müşteri Yoksa
            if (chosenCustomer == null) {
                newCustomer.removalTime = 0;
                chosenCustomer = newCustomer;
                System.out.println("ID : " + chosenCustomer.id + " Müşteri Kasaya Girdi");
            } else {
                // Kasadaki Müşterinin işi bitti mi?
                if (chosenCustomer.processTime < newCustomer.arrivalTime) {
                    newCustomer.arrivalTime -= chosenCustomer.processTime;
                    // chosenCustomer.processTime = 0;

                    // Kasaya yeni müşteri alınıyor.
                    if (isEmpty()) {
                        chosenCustomer = newCustomer;
                        System.out.println("ID : " + chosenCustomer.id + " Müşteri Kasaya Girdi");
                    } else {
                        enqueue(newCustomer);
                        chosenCustomer = chooseCustomer();
                        System.out.println("ID : " + chosenCustomer.id + " Müşteri Kasaya Girdi");
                    }
                } else {
                    newCustomer.removalTime = chosenCustomer.processTime - newCustomer.arrivalTime;
                    chosenCustomer.processTime -= newCustomer.arrivalTime;
                    addRemovalTimeToCustomers(newCustomer);
                    enqueue(newCustomer);
                    System.out.println("ID : " + chosenCustomer.id + " Müşteri Kasada");
                }
            }

            printCurrentQueue();
            System.out.println();
        }

        System.out.println("Müşteri Alımımız Sonlanmıştır.\nSıradaki Müşteriler için işlem yapılacaktır.\n");

        int tempNumberOfElements = numberOfElements;

        for (int i = 0; i < tempNumberOfElements; i++) {
            addProcessTimeToCustomers(chosenCustomer);
            chosenCustomer = chooseCustomer();
            System.out.println("ID : " + chosenCustomer.id + " Müşteri Kasaya Girdi");

            printCurrentQueue();
            System.out.println();
        }

        System.out.println("Mağazamız Kapanmıştır\n");
    }

    private void printCurrentQueue() {

        if (head != null) {
            Customer.printHead();

            Node temp = head;
            while (temp != null) {
                temp.key.printItem();
                temp = temp.next;
            }
        }
    }

    /**
     * Kasadaki Müşterinin İşlem Süresini Sıradaki Müşterilerin Bekleme Süresine
     * Ekler
     */
    private void addProcessTimeToCustomers(Customer chosenCustomer) {
        Node tmp = head;
        while (tmp != null) {
            tmp.key.removalTime += chosenCustomer.processTime;
            tmp = tmp.next;
        }
    }

    /**
     * Yeni Müşterinin Geliş Süresini Sıradaki Müşterilerin Bekleme Süresine Ekler
     */
    private void addRemovalTimeToCustomers(Customer newCustomer) {
        Node tmp = head;
        while (tmp != null) {
            tmp.key.removalTime += newCustomer.arrivalTime;
            tmp = tmp.next;
        }
    }

    /**
     * kuyrugun basindaki musteriyi yada tekerleme ile buldugu musteriyi return eder
     */
    public Customer chooseCustomer() {
        if (isEmpty()) {
            return null;
        }

        if (head.key.removalTime > 10) {
            Customer customer = head.key;
            head = head.next;
            return customer;
        } else {
            return dequeuWithCounting(tekerleme);
        }
    }

    public static void main(String[] args) {

        new CrazyMarket(10);
        
    }

    @Override
    public Iterator<Customer> iterator() {
        return Customer.customerList.iterator();
    }

    @Override
    public int size() {
        return numberOfElements;
    }

    @Override
    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    @Override
    public boolean enqueue(Customer item) {

        if (isEmpty()) {
            head = new Node(item);
        } else {

            Node tmpNode = head;

            while (tmpNode.next != null) {
                tmpNode = tmpNode.next;
            }

            tmpNode.next = new Node(item);
        }

        numberOfElements++;
        return true;

    }

    @Override
    public Customer dequeuNext() {

        if (isEmpty()) {
            return null;
        }

        Customer removedCustomer = head.key;
        head = head.next;
        numberOfElements--;
        return removedCustomer;
    }

    @Override
    public Customer dequeuWithCounting(String tekerleme) {
        int syllableCount = getSyllableCount(tekerleme); // 3

        // Node selectedNodePre = null;
        // Node selectedNode = head;

        // Store head node
        Node tempNode = head;
        Node prevNode = null;

        Customer dequeuCustomer = null;

        if (head.next == null) {
            dequeuCustomer = head.key;
            head = null;
            numberOfElements--;
            return dequeuCustomer;
        }

        for (int i = 0; i < syllableCount; i++) {
            if (tempNode.next == null) {
                break;
            } else {
                prevNode = tempNode;
                tempNode = tempNode.next;
            }
        }

        if (tempNode != null && prevNode != null) {
            prevNode.next = tempNode.next;
        }

        dequeuCustomer = tempNode.key;
        tempNode = null;

        numberOfElements--;

        return dequeuCustomer;

    }

    public int getSyllableCount(String str) {
        String vowelLetters = "aeıioöuü";
        int syllablesCount = 0;

        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < vowelLetters.length(); j++) {
                if (vowelLetters.charAt(j) == str.charAt(i)) {
                    syllablesCount++;
                }
            }
        }

        if (syllablesCount % numberOfElements == 0) {
            return numberOfElements;
        }

        return syllablesCount % numberOfElements;
    }
}