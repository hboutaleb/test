namespace SendMailTest
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.tbExpediteur = new System.Windows.Forms.TextBox();
            this.tbDestinataire = new System.Windows.Forms.TextBox();
            this.tbMessage = new System.Windows.Forms.TextBox();
            this.btEnvoyer = new System.Windows.Forms.Button();
            this.TextPass = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // tbExpediteur
            // 
            this.tbExpediteur.Location = new System.Drawing.Point(199, 37);
            this.tbExpediteur.Name = "tbExpediteur";
            this.tbExpediteur.Size = new System.Drawing.Size(168, 20);
            this.tbExpediteur.TabIndex = 0;
            this.tbExpediteur.TextChanged += new System.EventHandler(this.tbExpediteur_TextChanged);
            // 
            // tbDestinataire
            // 
            this.tbDestinataire.Location = new System.Drawing.Point(199, 72);
            this.tbDestinataire.Name = "tbDestinataire";
            this.tbDestinataire.Size = new System.Drawing.Size(168, 20);
            this.tbDestinataire.TabIndex = 1;
            this.tbDestinataire.TextChanged += new System.EventHandler(this.tbDestinataire_TextChanged);
            // 
            // tbMessage
            // 
            this.tbMessage.Location = new System.Drawing.Point(199, 134);
            this.tbMessage.Multiline = true;
            this.tbMessage.Name = "tbMessage";
            this.tbMessage.Size = new System.Drawing.Size(249, 76);
            this.tbMessage.TabIndex = 3;
            // 
            // btEnvoyer
            // 
            this.btEnvoyer.Location = new System.Drawing.Point(204, 216);
            this.btEnvoyer.Name = "btEnvoyer";
            this.btEnvoyer.Size = new System.Drawing.Size(95, 23);
            this.btEnvoyer.TabIndex = 4;
            this.btEnvoyer.Text = "Envoyer";
            this.btEnvoyer.UseVisualStyleBackColor = true;
            this.btEnvoyer.Click += new System.EventHandler(this.btEnvoyer_Click);
            // 
            // TextPass
            // 
            this.TextPass.CausesValidation = false;
            this.TextPass.Location = new System.Drawing.Point(199, 99);
            this.TextPass.Name = "TextPass";
            this.TextPass.Size = new System.Drawing.Size(105, 20);
            this.TextPass.TabIndex = 5;
            this.TextPass.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(134, 40);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(63, 13);
            this.label1.TabIndex = 7;
            this.label1.Text = "Expediteur :";
            this.label1.Click += new System.EventHandler(this.label1_Click_1);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(137, 72);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(38, 13);
            this.label2.TabIndex = 7;
            this.label2.Text = "Objet :";
            this.label2.Click += new System.EventHandler(this.label1_Click_1);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(137, 99);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(62, 13);
            this.label3.TabIndex = 7;
            this.label3.Text = "PassWord :";
            this.label3.Click += new System.EventHandler(this.label1_Click_1);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(137, 137);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(34, 13);
            this.label4.TabIndex = 7;
            this.label4.Text = "Text :";
            this.label4.Click += new System.EventHandler(this.label1_Click_1);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(667, 323);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.TextPass);
            this.Controls.Add(this.btEnvoyer);
            this.Controls.Add(this.tbMessage);
            this.Controls.Add(this.tbDestinataire);
            this.Controls.Add(this.tbExpediteur);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox tbExpediteur;
        private System.Windows.Forms.TextBox tbDestinataire;
        private System.Windows.Forms.TextBox tbMessage;
        private System.Windows.Forms.Button btEnvoyer;
        private System.Windows.Forms.TextBox TextPass;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
    }
}

