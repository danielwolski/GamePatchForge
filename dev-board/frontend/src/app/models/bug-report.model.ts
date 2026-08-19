export interface BugReport {
  uuid: string;
  summary: string;
  description: string;
  gameVersion: string;
  os: string;
  cpu: string;
  gpu: string;
  ram: number;
}
