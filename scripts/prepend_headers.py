import os
import git
import re

# Define the path to your Eclipse project
project_path = '../..'

# Initialize a Git repository object
repo = git.Repo(project_path)

# Function to get commit history for a file
def get_commit_history(file_path):
    history = []
    file_path = os.path.relpath(file_path, project_path)
    
    for commit in repo.iter_commits(paths=file_path):
        commit_info = {
            'commit_id': commit.hexsha,
            'date': commit.authored_datetime,
            'author': commit.author.name,
            'comment': commit.message,
        }
        history.append(commit_info)
    
    return history

# Function to update Java files with Git commit history
def update_java_files_with_history(directory):
    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                commit_history = get_commit_history(file_path)

                if commit_history:
                    with open(file_path, 'r+') as java_file:
                        content = java_file.read()
                        java_file.seek(0, 0)
                        java_file.write(create_file_header(commit_history) + content)

# Function to create a file header
def create_file_header(commit_history):
    header = '/***************************************************************************************************\n'
    for commit_info in commit_history:
        header += f'Commit: {commit_info["commit_id"]}\n'
        header += f'Date: {commit_info["date"]}\n'
        header += f'Author: {commit_info["author"]}\n'
        header += f'Comment: {commit_info["comment"]}\n'
        header += '\n'
    header += '/***************************************************************************************************\n\n'
    return header

# Update Java files with Git commit history
update_java_files_with_history(project_path)
