package com.moussif.tawfi.myapp;

public class Note {
        private String noteText;
        int noteId;
        private String title;


        public Note() {
        }

        public Note(int noteId,String title, String noteText) {
            this.noteId=noteId;
            this.noteText = noteText;
            this.title = title;
        }
        public Note(String title, String noteText) {
            this.noteText = noteText;
            this.title = title;
        }

        public int getNoteId() {
            return noteId;
        }

        public void setNoteId(int noteId) {
            this.noteId = noteId;
        }

        public String getNoteText() {
            return noteText;
        }

        public void setNoteText(String noteText) {
            this.noteText = noteText;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
}
