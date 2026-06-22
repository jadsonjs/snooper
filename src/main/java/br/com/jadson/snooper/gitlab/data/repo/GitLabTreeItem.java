package br.com.jadson.snooper.gitlab.data.repo;


/**
 * Information about an item in the GitLab repository tree.
 * It can represent either a file or a directory.
 *
 * Luisa Ferreira - luisaferreirass08@gmail.com
 */
public class GitLabTreeItem {
    public String  name;
    public String path;
    public String type; // "blob" = arquivo, "tree" = diretório
    public long size;

}
