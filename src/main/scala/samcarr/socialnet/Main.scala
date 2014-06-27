package samcarr.socialnet

object Main {

  def main(args: Array[String]): Unit = {
    println("Welcome to SocialNet!");
    
    val net = SocialNet()
    val cli = new Cli()
    cli.session(net)
  }
}