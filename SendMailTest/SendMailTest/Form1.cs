using System;
using System.Net;
using System.Net.Mail;
using System.Web;
using System.Linq; 
using System.Windows.Forms;


namespace SendMailTest
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void btEnvoyer_Click(object sender, EventArgs e)
        {
            MailMessage msg = new MailMessage("boutalebhocine90@gmail.com", tbExpediteur.Text, tbDestinataire.Text, tbMessage.Text);
            msg.IsBodyHtml = true;
            msg.Body = "<html>"
            + "<head>"
            + "<meta http-equiv=\"Content-Language\" content=\"fr\">"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">"
            + "</head>"
            + "<body>"
            + "<p>Mon premier mail au format HTML</p>"
            + "</body>"
            + "</html>";
            msg.To.Add("hocineboutaleb@yahoo.fr");
            string chemin;
            chemin = "C:\\Users\\hboutaleb\\Documents\\Stage Hocine\\logo.GIF";
            msg.Attachments.Add(new System.Net.Mail.Attachment(chemin));
            SmtpClient sc = new SmtpClient("smtp.gmail.com ",587);
            sc.UseDefaultCredentials = false;
            NetworkCredential cre = new NetworkCredential("boutalebhocine90@gmail.com",TextPass.Text);
            sc.Credentials = cre;
            sc.EnableSsl = true;
            sc.Send(msg);
            MessageBox.Show("Mail Sent");

        }

        private void TextPass_TextChanged(object sender, EventArgs e)
        {
            TextPass.PasswordChar = '*';
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            TextPass.PasswordChar = '*';
        }

        private void label1_Click_1(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void openFileDialog1_FileOk(object sender, System.ComponentModel.CancelEventArgs e)
        {

        }

        private void openFileDialog1_FileOk_1(object sender, System.ComponentModel.CancelEventArgs e)
        {

        }

        private void tbExpediteur_TextChanged(object sender, EventArgs e)
        {

        }

        private void tbDestinataire_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
