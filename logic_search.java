package advaAlgo_SearchEng;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class logic_search {
	
	private int c;
	private String []htmls;
	private Trie T1;
	Web_Crawler crawler;
	
	public String[] get_html() 
	{
		return htmls;
	}

	public void set_html(String[] htmls) 
	{
		this.htmls = htmls;
	}
	
	public void setCount(int count) 
	{
		this.c = count;
	}
	public int getCount()
	{
		return c;
	}

	public int insert(){
		
		int index = 0;
		for(String html : get_html()){
			crawler = new Web_Crawler(html);
			
			ArrayList<String> list = crawler.getWords();
			for(String l: list){
				T1.insert(l.toLowerCase(), index);
			}
			index++;
			if(index == c){
				break;
			}
		}
		return 0;
		
	}
	
	public logic_search(int count, String []htmls) {
		// TODO Auto-generated constructor stub
		setCount(count);
		set_html(htmls);
		T1 = new Trie();
		
	}
	
	
	public ArrayList<String> search(String s){
		
		String []words = s.split(" ");
		String []sresults = new String[words.length];
		int len = 0;
		for(; len < words.length; len++){
			sresults[len] = T1.search(words[len]);
		}
		HashMap<String, Integer> map = new HashMap<>();
		String temp = "";
		for(len = 0; len < words.length; len++){
			String[] spl = sresults[len].split(" ");
			for(String sp :spl){
				if(!temp.equals(sp)){
					temp = "" + sp;
					if(map.containsKey(sp)){
						int val = map.get(sp);
						map.put(sp, ++val);
					}
					else{
						map.put(sp, 1);
					}
				}
			}
		}
		
	ArrayList<String> list = new ArrayList<>();
				
	for(String key: map.keySet())
	{
			if(map.get(key)==words.length && !key.equals("-1")){
				list.add(key);
			}
		}
		
		return list;
		
	}
	
	public class Trie 
	{
	    private class TrieNode 
	    {
	        Map<Character, TrieNode> child;
	        boolean end;
	        String index;
	        public TrieNode() 
	        {
	            child = new HashMap<>();
	            end = false;
	            index = "";
	        }
	    }

	    private final TrieNode root;
	    public Trie() {
	        root = new TrieNode();
	    }

	    public void insert(String word,int index) //Insert operation implementation in trie
	    {
	        TrieNode current = root;
	        for (int i = 0; i < word.length(); i++) 
	        {
	            char ch = word.charAt(i);
	            TrieNode node = current.child.get(ch);
	            if (node == null) 
	            {
	                node = new TrieNode();
	                current.child.put(ch, node);
	            }
	            current = node;
	        }
	        current.index = current.index + "" + index + " "; //mark the current nodes endOfWord as true
	        current.end = true;
	        return;
	    }


	    public void insertRecursive(String word,int index) //Recursive implementation of insert into trie
	    {
	        insertRecursive(root, word, 0, index);
	        return;
	    }


	    private void insertRecursive(TrieNode current, String word, int index, int i) 
	    {
	        if (index == word.length()) 
	        {
	            
	        	current.index = current.index + "" + index + " "; //if end of word is reached then mark endOfWord as true on current node
	            current.end = true;
	            return;
	        }
	        char ch = word.charAt(index);
	        TrieNode node = current.child.get(ch);

	        if (node == null)  //if node does not exists in map, then create one and add it into map
	        {
	            node = new TrieNode();
	            current.child.put(ch, node);
	        }
	        insertRecursive(node, word, index + 1, i);
	        
	    }

	    /**
	     * Iterative implementation of search into trie.
	     */
	    public String search(String word) 
	    {
	        TrieNode current = root;
	        for (int i = 0; i < word.length(); i++) 
	        {
	            char ch = word.charAt(i);
	            TrieNode node = current.child.get(ch);
	           
	            if (node == null) //if node does not exist for given char then return false
	            {
	                return "-1";
	            }
	            current = node;
	        }
	        
	        if(!current.end) //return true of current's endOfWord is true else return false.
	        {
	        	current.end=true;
	        	return current.index;
	        }
	        
	        if(current.end)
	        {
	        	return current.index;
	        }
	        return "-1";
	    }

	    public String searchforRecursive(String word) //Recursive implementation of search into trie.
	    {
	        return searchforRecursive(root, word, 0);
	    }
	    
	    private String searchforRecursive(TrieNode current, String word, int index) 
	    {
	        if (index == word.length()) 
	        {
	            return current.index;  //return true of current's endOfWord is true else return false.
	        }
	        char ch = word.charAt(index);
	        
	        TrieNode node = current.child.get(ch);
	        
	        if (node == null) //if node does not exist for given char then return false 
	        {
	            return "-1";
	        }
	        return searchforRecursive(node, word, index + 1);
	    }

	    public void remove(String word) //Delete word from tree.
	    {
	        remove(root, word, 0);
	    }
        
	    /*
	      Returns true if parent should delete the mapping
	    */
	    
	    private boolean remove(TrieNode curr, String word, int index) 
	    {
	        if (index == word.length()) 
	        {
	            
	            if (!curr.end) //when end of word is reached only delete if currrent.endOfWord is true.
	            {
	                return false;
	            }
	            curr.end = false;
	           
	            return curr.child.size() == 0; //if current has no other mapping then return true
	        }
	        char ch = word.charAt(index);
	        TrieNode node = curr.child.get(ch);
	        if (node == null) 
	        {
	            return false;
	        }
	        boolean DeleteCurrentNode = remove(node, word, index + 1);

	        if (DeleteCurrentNode)  //if true is returned then delete the mapping of character and trienode reference from map.
	        {
	            curr.child.remove(ch);
	           
	            return curr.child.size() == 0;  //return true if no mappings are left in the map.
	        }
	        return false;
	    }
	}
}
