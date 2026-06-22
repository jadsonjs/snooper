package br.com.jadson.snooper.github.data.repo;

import java.util.List;

/**
 * Information about the file tree of a GitHub repository.
 *
 * Luisa Ferreira - luisaferreirass08@gmail.com
 */
public class GitHubTreeInfo {
    public String sha;
    public List<GitHubTreeItem> tree;

    /**
     * Information about an item in the GitHub repository tree.
     * It can represent either a file or a directory.
     *
     * Luisa Ferreira - luisaferreirass08@gmail.com
     */
    public static class GitHubTreeItem {
        public String path;
        public String type; // "blob" = arquivo, "tree" = diretório
        public long size;
    }
}
