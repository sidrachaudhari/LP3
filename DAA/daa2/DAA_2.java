import java.util.*;

// Huffman Encoding — Greedy Strategy
// Time: O(n log n)  Space: O(n)
public class DAA_2 {

    static class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left, right;

        Node(char ch, int freq) { this.ch = ch; this.freq = freq; }
        Node(int freq, Node l, Node r) { this.ch = '\0'; this.freq = freq; left = l; right = r; }
        boolean isLeaf() { return left == null && right == null; }
        public int compareTo(Node o) { return freq - o.freq; }
    }

    static Node buildTree(Map<Character, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (var e : freq.entrySet()) pq.add(new Node(e.getKey(), e.getValue()));
        if (pq.size() == 1) { Node n = pq.poll(); return new Node(n.freq, n, null); }
        while (pq.size() > 1) {
            Node l = pq.poll(), r = pq.poll();
            pq.add(new Node(l.freq + r.freq, l, r));
        }
        return pq.poll();
    }

    static void getCodes(Node node, String code, Map<Character, String> codes) {
        if (node == null) return;
        if (node.isLeaf()) { codes.put(node.ch, code.isEmpty() ? "0" : code); return; }
        getCodes(node.left, code + "0", codes);
        getCodes(node.right, code + "1", codes);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter string: ");
        String text = sc.nextLine();

        // Build frequency map
        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (char c : text.toCharArray()) freq.merge(c, 1, Integer::sum);

        System.out.println("\nCharacter Frequencies:");
        freq.forEach((k, v) -> System.out.println("  '" + k + "' : " + v));

        // Build tree and generate codes
        Node root = buildTree(freq);
        Map<Character, String> codes = new HashMap<>();
        getCodes(root, "", codes);

        System.out.println("\nHuffman Codes:");
        codes.forEach((k, v) -> System.out.println("  '" + k + "' : " + v));

        // Encode
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) encoded.append(codes.get(c));
        System.out.println("\nEncoded: " + encoded);

        // Decode
        StringBuilder decoded = new StringBuilder();
        Node cur = root;
        for (char bit : encoded.toString().toCharArray()) {
            cur = (bit == '0') ? cur.left : cur.right;
            if (cur.isLeaf()) { decoded.append(cur.ch); cur = root; }
        }
        System.out.println("Decoded: " + decoded);
    }
}
