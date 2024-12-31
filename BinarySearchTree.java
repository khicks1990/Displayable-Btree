public class BinarySearchTree extends BinaryTree {

    /**
     * A private class representing the result of a removal operation.
     * It contains the removed node and the remaining tree.
     */
    private class RemovalResult {
        private Node node; // The removed node
        private Node tree; // The remaining tree

        public RemovalResult(Node node, Node tree) {
            this.node = node;
            this.tree = tree;
        }
    }

    /**
     * Adds a new value to the binary search tree.
     *
     * @param x the value to be added
     * @return true if the value was added successfully, false otherwise
     */
    public boolean add(int x) {
        root = add(x, root); // Call the private add method with the root node
        return true; // Return true indicating successful addition
    }

    /**
     * Private helper method to recursively add a value to the tree.
     *
     * @param x      the value to be added
     * @param bstree the root of the current subtree
     * @return the modified tree after adding the value
     */
    private Node add(int x, Node bstree) {
        if (bstree == null) {
            // If the tree is empty, create a new node with the value x
            return new Node(x);
        } else if (x < bstree.value) {
            // If x is less than the current node's value, add x to the left subtree
            bstree.left = add(x, bstree.left);
        } else if (x >= bstree.value) {
            // If x is greater than or equal to the current node's value, add x to the right subtree
            bstree.right = add(x, bstree.right);
        }
        return bstree; // Return the modified tree
    }

    /**
     * Checks if the binary search tree contains a given value.
     *
     * @param x the value to search for
     * @return true if the value is found, false otherwise
     */
    public boolean contains(int x) {
        return contains(x, root); // Call the private recursive contains method with the root node
    }

    /**
     * Private helper method to recursively search for a value in the tree.
     *
     * @param x      the value to search for
     * @param bstree the root of the current subtree
     * @return true if the value is found, false otherwise
     */
    private boolean contains(int x, Node bstree) {
        if (bstree == null) {
            // If the tree is empty or the end of the tree is reached, x is not found
            return false;
        } else if (x == bstree.value) {
            // If x is equal to the current node's value, x is found
            return true;
        } else if (x < bstree.value) {
            // If x is less than the current node's value, search in the left subtree
            return contains(x, bstree.left);
        } else {
            // If x is greater than the current node's value, search in the right subtree
            return contains(x, bstree.right);
        }
    }

    /**
     * Removes a value from the binary search tree.
     *
     * @param x the value to be removed
     * @return true if the value was removed successfully, false otherwise
     */
    public boolean remove(int x) {
        RemovalResult result = remove(root, x); // Call the private remove method with the root node and x
        if (result == null) {
            // If the removal result is null, x was not found
            return false;
        } else {
            root = result.tree; // Update the root of the tree
            return true; // Return true indicating successful removal
        }
    }

    /**
     * Private helper method to recursively remove a value from the tree.
     *
     * @param bTree the root of the current subtree
     * @param x     the value to be removed
     * @return the RemovalResult object containing the removed node and the modified tree
     */
    private RemovalResult remove(Node bTree, int x) {
        if (bTree == null) {
            // If the tree is empty or the end of the tree is reached, x is not found
            return null;
        }

        if (x < bTree.value) {
            // If x is less than the current node's value, continue searching in the left subtree
            RemovalResult result = remove(bTree.left, x);
            if (result != null) {
                // If the value x is found, update the left child of the current node
                bTree.left = result.tree;
            }
            return result;
        } else if (x > bTree.value) {
            // If x is greater than the current node's value, continue searching in the right subtree
            RemovalResult result = remove(bTree.right, x);
            if (result != null) {
                // If the value x is found, update the right child of the current node
                bTree.right = result.tree;
            }
            return result;
        } else {
            // If x is equal to the current node's value, x is found and needs to be removed
            if (bTree.left == null && bTree.right == null) {
                // Case 1: The node is a leaf (it has no children)
                return new RemovalResult(bTree, null);
            } else if (bTree.left != null && bTree.right != null) {
                // Case 2: The node has two children
                RemovalResult remResult = removeLargest(bTree.left); // Find the largest node in the left subtree
                Node newRoot = remResult.node; // Largest node becomes the new root
                newRoot.left = remResult.tree; // Update the left child of the new root
                newRoot.right = bTree.right; // Update the right child of the new root
                return new RemovalResult(bTree, newRoot);
            } else {
                // Case 3: The node has only one child
                Node tree = (bTree.left != null) ? bTree.left : bTree.right; // Determine the child of the current node
                return new RemovalResult(bTree, tree);
            }
        }
    }

    /**
     * Private helper method to remove the largest node from a given subtree.
     *
     * @param bTree the root of the current subtree
     * @return the RemovalResult object containing the removed largest node and the modified subtree
     */
    private RemovalResult removeLargest(Node bTree) {
        if (bTree.right == null) {
            // If the right child is null, it means the current node is the largest node
            // We remove this node by returning a RemovalResult object with the current node and its left child
            return new RemovalResult(bTree, bTree.left);
        }

        // If the right child is not null, we recursively call removeLargest on the right child
        RemovalResult remResult = removeLargest(bTree.right);

        // We update the right child of the current node with the modified subtree returned from removeLargest
        bTree.right = remResult.tree;

        // We return the original largest node as the node and the modified subtree as the tree in the RemovalResult object
        return new RemovalResult(bTree, remResult.node);
    }
}